package org.activiti.diagram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessInstanceHighlightsResource {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    protected ObjectMapper objectMapper = new ObjectMapper();

    public ProcessInstanceHighlightsResource() {
    }

    @RequestMapping(
            value = {"/process-instance/{processInstanceId}/highlights"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public ObjectNode getHighlighted(@PathVariable String processInstanceId) {
        ObjectNode responseJSON = this.objectMapper.createObjectNode();
        responseJSON.put("processInstanceId", processInstanceId);
        ArrayNode activitiesArray = this.objectMapper.createArrayNode();
        ArrayNode flowsArray = this.objectMapper.createArrayNode();

        try {
            ProcessInstance e = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(e.getProcessDefinitionId());
            responseJSON.put("processDefinitionId", e.getProcessDefinitionId());
            List highLightedActivities = this.runtimeService.getActiveActivityIds(processInstanceId);
            List highLightedFlows = this.getHighLightedFlows(processDefinition, processInstanceId);
            Iterator var9 = highLightedActivities.iterator();

            String flow;
            while(var9.hasNext()) {
                flow = (String)var9.next();
                activitiesArray.add(flow);
            }

            var9 = highLightedFlows.iterator();

            while(var9.hasNext()) {
                flow = (String)var9.next();
                flowsArray.add(flow);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        responseJSON.put("activities", activitiesArray);
        responseJSON.put("flows", flowsArray);
        return responseJSON;
    }

    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition, String processInstanceId) {
        ArrayList highLightedFlows = new ArrayList();
        List historicActivityInstances = this.historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        LinkedList hisActInstList = new LinkedList();
        hisActInstList.addAll(historicActivityInstances);
        this.getHighlightedFlows(processDefinition.getActivities(), hisActInstList, highLightedFlows);
        return highLightedFlows;
    }

    private void getHighlightedFlows(List<ActivityImpl> activityList, LinkedList<HistoricActivityInstance> hisActInstList, List<String> highLightedFlows) {
        ArrayList startEventActList = new ArrayList();
        HashMap activityMap = new HashMap(activityList.size());
        Iterator firstHistActInst = activityList.iterator();

        while(firstHistActInst.hasNext()) {
            ActivityImpl firstActType = (ActivityImpl)firstHistActInst.next();
            activityMap.put(firstActType.getId(), firstActType);
            String histActInst = (String)firstActType.getProperty("type");
            if(histActInst != null && histActInst.toLowerCase().indexOf("startevent") >= 0) {
                startEventActList.add(firstActType);
            }
        }

        HistoricActivityInstance firstHistActInst1 = (HistoricActivityInstance)hisActInstList.getFirst();
        String firstActType1 = firstHistActInst1.getActivityType();
        if(firstActType1 != null && firstActType1.toLowerCase().indexOf("startevent") < 0) {
            PvmTransition histActInst1 = this.getStartTransaction(startEventActList, firstHistActInst1);
            if(histActInst1 != null) {
                highLightedFlows.add(histActInst1.getId());
            }
        }

        while(true) {
            ActivityImpl activity;
            HistoricActivityInstance histActInst2;
            do {
                if(hisActInstList.isEmpty()) {
                    return;
                }

                histActInst2 = (HistoricActivityInstance)hisActInstList.removeFirst();
                activity = (ActivityImpl)activityMap.get(histActInst2.getActivityId());
            } while(activity == null);

            boolean isParallel = false;
            String type = histActInst2.getActivityType();
            if(!"parallelGateway".equals(type) && !"inclusiveGateway".equals(type)) {
                if("subProcess".equals(histActInst2.getActivityType())) {
                    this.getHighlightedFlows(activity.getActivities(), hisActInstList, highLightedFlows);
                }
            } else {
                isParallel = true;
            }

            ArrayList allOutgoingTrans = new ArrayList();
            allOutgoingTrans.addAll(activity.getOutgoingTransitions());
            allOutgoingTrans.addAll(this.getBoundaryEventOutgoingTransitions(activity));
            List activityHighLightedFlowIds = this.getHighlightedFlows(allOutgoingTrans, hisActInstList, isParallel);
            highLightedFlows.addAll(activityHighLightedFlowIds);
        }
    }

    private PvmTransition getStartTransaction(List<ActivityImpl> startEventActList, HistoricActivityInstance firstActInst) {
        Iterator var3 = startEventActList.iterator();

        while(var3.hasNext()) {
            ActivityImpl startEventAct = (ActivityImpl)var3.next();
            Iterator var5 = startEventAct.getOutgoingTransitions().iterator();

            while(var5.hasNext()) {
                PvmTransition trans = (PvmTransition)var5.next();
                if(trans.getDestination().getId().equals(firstActInst.getActivityId())) {
                    return trans;
                }
            }
        }

        return null;
    }

    private List<PvmTransition> getBoundaryEventOutgoingTransitions(ActivityImpl activity) {
        ArrayList boundaryTrans = new ArrayList();
        Iterator var3 = activity.getActivities().iterator();

        while(var3.hasNext()) {
            ActivityImpl subActivity = (ActivityImpl)var3.next();
            String type = (String)subActivity.getProperty("type");
            if(type != null && type.toLowerCase().indexOf("boundary") >= 0) {
                boundaryTrans.addAll(subActivity.getOutgoingTransitions());
            }
        }

        return boundaryTrans;
    }

    private List<String> getHighlightedFlows(List<PvmTransition> pvmTransitionList, LinkedList<HistoricActivityInstance> hisActInstList, boolean isParallel) {
        ArrayList highLightedFlowIds = new ArrayList();
        PvmTransition earliestTrans = null;
        HistoricActivityInstance earliestHisActInst = null;
        Iterator var7 = pvmTransitionList.iterator();

        while(true) {
            while(true) {
                PvmTransition pvmTransition;
                HistoricActivityInstance destHisActInst;
                do {
                    if(!var7.hasNext()) {
                        if(!isParallel && earliestTrans != null) {
                            highLightedFlowIds.add(earliestTrans.getId());
                        }

                        return highLightedFlowIds;
                    }

                    pvmTransition = (PvmTransition)var7.next();
                    String destActId = pvmTransition.getDestination().getId();
                    destHisActInst = this.findHisActInst(hisActInstList, destActId);
                } while(destHisActInst == null);

                if(isParallel) {
                    highLightedFlowIds.add(pvmTransition.getId());
                } else if(earliestHisActInst == null || earliestHisActInst.getId().compareTo(destHisActInst.getId()) > 0) {
                    earliestTrans = pvmTransition;
                    earliestHisActInst = destHisActInst;
                }
            }
        }
    }

    private HistoricActivityInstance findHisActInst(LinkedList<HistoricActivityInstance> hisActInstList, String actId) {
        Iterator var3 = hisActInstList.iterator();

        HistoricActivityInstance hisActInst;
        do {
            if(!var3.hasNext()) {
                return null;
            }

            hisActInst = (HistoricActivityInstance)var3.next();
        } while(!hisActInst.getActivityId().equals(actId));

        return hisActInst;
    }
}