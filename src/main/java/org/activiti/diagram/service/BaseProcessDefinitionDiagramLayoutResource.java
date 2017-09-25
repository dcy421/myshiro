package org.activiti.diagram.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.bpmn.behavior.BoundaryEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.ErrorEventDefinition;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.Lane;
import org.activiti.engine.impl.pvm.process.LaneSet;
import org.activiti.engine.impl.pvm.process.ParticipantProcess;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseProcessDefinitionDiagramLayoutResource {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    public BaseProcessDefinitionDiagramLayoutResource() {
    }

    public ObjectNode getDiagramNode(String processInstanceId, String processDefinitionId) {
        List highLightedFlows = Collections.emptyList();
        List highLightedActivities = Collections.emptyList();
        HashMap subProcessInstanceMap = new HashMap();
        ProcessInstance processInstance = null;
        if(processInstanceId != null) {
            processInstance = (ProcessInstance)this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(processInstance == null) {
                throw new ActivitiObjectNotFoundException("Process instance could not be found");
            }

            processDefinitionId = processInstance.getProcessDefinitionId();
            List processDefinition = this.runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstanceId).list();
            Iterator responseJSON = processDefinition.iterator();

            while(responseJSON.hasNext()) {
                ProcessInstance pdrJSON = (ProcessInstance)responseJSON.next();
                String sequenceFlowArray = pdrJSON.getProcessDefinitionId();
                String activityArray = ((ExecutionEntity)pdrJSON).getSuperExecutionId();
                ProcessDefinitionEntity laneSet = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(sequenceFlowArray);
                ObjectNode activity = (new ObjectMapper()).createObjectNode();
                activity.put("processInstanceId", pdrJSON.getId());
                activity.put("superExecutionId", activityArray);
                activity.put("processDefinitionId", laneSet.getId());
                activity.put("processDefinitionKey", laneSet.getKey());
                activity.put("processDefinitionName", laneSet.getName());
                subProcessInstanceMap.put(activityArray, activity);
            }
        }

        if(processDefinitionId == null) {
            throw new ActivitiObjectNotFoundException("No process definition id provided");
        } else {
            ProcessDefinitionEntity processDefinition1 = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(processDefinitionId);
            if(processDefinition1 == null) {
                throw new ActivitiException("Process definition " + processDefinitionId + " could not be found");
            } else {
                ObjectNode responseJSON1 = (new ObjectMapper()).createObjectNode();
                JsonNode pdrJSON1 = this.getProcessDefinitionResponse(processDefinition1);
                if(pdrJSON1 != null) {
                    responseJSON1.put("processDefinition", pdrJSON1);
                }

                ArrayNode sequenceFlowArray1;
                ArrayNode activityArray1;
                Iterator laneSet1;
                if(processInstance != null) {
                    sequenceFlowArray1 = (new ObjectMapper()).createArrayNode();
                    activityArray1 = (new ObjectMapper()).createArrayNode();
                    highLightedActivities = this.runtimeService.getActiveActivityIds(processInstanceId);
                    highLightedFlows = this.getHighLightedFlows(processInstanceId, processDefinition1);
                    laneSet1 = highLightedActivities.iterator();

                    String activity1;
                    while(laneSet1.hasNext()) {
                        activity1 = (String)laneSet1.next();
                        sequenceFlowArray1.add(activity1);
                    }

                    laneSet1 = highLightedFlows.iterator();

                    while(laneSet1.hasNext()) {
                        activity1 = (String)laneSet1.next();
                        activityArray1.add(activity1);
                    }

                    responseJSON1.put("highLightedActivities", sequenceFlowArray1);
                    responseJSON1.put("highLightedFlows", activityArray1);
                }

                if(processDefinition1.getParticipantProcess() != null) {
                    ParticipantProcess sequenceFlowArray2 = processDefinition1.getParticipantProcess();
                    ObjectNode activityArray2 = (new ObjectMapper()).createObjectNode();
                    activityArray2.put("id", sequenceFlowArray2.getId());
                    if(StringUtils.isNotEmpty(sequenceFlowArray2.getName())) {
                        activityArray2.put("name", sequenceFlowArray2.getName());
                    } else {
                        activityArray2.put("name", "");
                    }

                    activityArray2.put("x", sequenceFlowArray2.getX());
                    activityArray2.put("y", sequenceFlowArray2.getY());
                    activityArray2.put("width", sequenceFlowArray2.getWidth());
                    activityArray2.put("height", sequenceFlowArray2.getHeight());
                    responseJSON1.put("participantProcess", activityArray2);
                }

                if(processDefinition1.getLaneSets() != null && !processDefinition1.getLaneSets().isEmpty()) {
                    sequenceFlowArray1 = (new ObjectMapper()).createArrayNode();
                    Iterator activityArray3 = processDefinition1.getLaneSets().iterator();

                    while(activityArray3.hasNext()) {
                        LaneSet laneSet2 = (LaneSet)activityArray3.next();
                        ArrayNode activity2 = (new ObjectMapper()).createArrayNode();
                        if(laneSet2.getLanes() != null && !laneSet2.getLanes().isEmpty()) {
                            Iterator laneSetJSON = laneSet2.getLanes().iterator();

                            while(laneSetJSON.hasNext()) {
                                Lane lane = (Lane)laneSetJSON.next();
                                ObjectNode laneJSON = (new ObjectMapper()).createObjectNode();
                                laneJSON.put("id", lane.getId());
                                if(StringUtils.isNotEmpty(lane.getName())) {
                                    laneJSON.put("name", lane.getName());
                                } else {
                                    laneJSON.put("name", "");
                                }

                                laneJSON.put("x", lane.getX());
                                laneJSON.put("y", lane.getY());
                                laneJSON.put("width", lane.getWidth());
                                laneJSON.put("height", lane.getHeight());
                                List flowNodeIds = lane.getFlowNodeIds();
                                ArrayNode flowNodeIdsArray = (new ObjectMapper()).createArrayNode();
                                Iterator var19 = flowNodeIds.iterator();

                                while(var19.hasNext()) {
                                    String flowNodeId = (String)var19.next();
                                    flowNodeIdsArray.add(flowNodeId);
                                }

                                laneJSON.put("flowNodeIds", flowNodeIdsArray);
                                activity2.add(laneJSON);
                            }
                        }

                        ObjectNode laneSetJSON1 = (new ObjectMapper()).createObjectNode();
                        laneSetJSON1.put("id", laneSet2.getId());
                        if(StringUtils.isNotEmpty(laneSet2.getName())) {
                            laneSetJSON1.put("name", laneSet2.getName());
                        } else {
                            laneSetJSON1.put("name", "");
                        }

                        laneSetJSON1.put("lanes", activity2);
                        sequenceFlowArray1.add(laneSetJSON1);
                    }

                    if(sequenceFlowArray1.size() > 0) {
                        responseJSON1.put("laneSets", sequenceFlowArray1);
                    }
                }

                sequenceFlowArray1 = (new ObjectMapper()).createArrayNode();
                activityArray1 = (new ObjectMapper()).createArrayNode();
                laneSet1 = processDefinition1.getActivities().iterator();

                while(laneSet1.hasNext()) {
                    ActivityImpl activity3 = (ActivityImpl)laneSet1.next();
                    this.getActivity(processInstanceId, activity3, activityArray1, sequenceFlowArray1, processInstance, highLightedFlows, subProcessInstanceMap);
                }

                responseJSON1.put("activities", activityArray1);
                responseJSON1.put("sequenceFlows", sequenceFlowArray1);
                return responseJSON1;
            }
        }
    }

    private List<String> getHighLightedFlows(String processInstanceId, ProcessDefinitionEntity processDefinition) {
        ArrayList highLightedFlows = new ArrayList();
        List historicActivityInstances = ((HistoricActivityInstanceQuery)this.historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc()).list();
        ArrayList historicActivityInstanceList = new ArrayList();
        Iterator highLightedActivities = historicActivityInstances.iterator();

        while(highLightedActivities.hasNext()) {
            HistoricActivityInstance hai = (HistoricActivityInstance)highLightedActivities.next();
            historicActivityInstanceList.add(hai.getActivityId());
        }

        List highLightedActivities1 = this.runtimeService.getActiveActivityIds(processInstanceId);
        historicActivityInstanceList.addAll(highLightedActivities1);
        Iterator hai1 = processDefinition.getActivities().iterator();

        while(true) {
            ActivityImpl activity;
            int index;
            do {
                do {
                    if(!hai1.hasNext()) {
                        return highLightedFlows;
                    }

                    activity = (ActivityImpl)hai1.next();
                    index = historicActivityInstanceList.indexOf(activity.getId());
                } while(index < 0);
            } while(index + 1 >= historicActivityInstanceList.size());

            List pvmTransitionList = activity.getOutgoingTransitions();
            Iterator var11 = pvmTransitionList.iterator();

            while(var11.hasNext()) {
                PvmTransition pvmTransition = (PvmTransition)var11.next();
                String destinationFlowId = pvmTransition.getDestination().getId();
                if(destinationFlowId.equals(historicActivityInstanceList.get(index + 1))) {
                    highLightedFlows.add(pvmTransition.getId());
                }
            }
        }
    }

    private void getActivity(String processInstanceId, ActivityImpl activity, ArrayNode activityArray, ArrayNode sequenceFlowArray, ProcessInstance processInstance, List<String> highLightedFlows, Map<String, ObjectNode> subProcessInstanceMap) {
        ObjectNode activityJSON = (new ObjectMapper()).createObjectNode();
        String multiInstance = (String)activity.getProperty("multiInstance");
        if(multiInstance != null && !"sequential".equals(multiInstance)) {
            multiInstance = "parallel";
        }

        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        Boolean collapsed = Boolean.valueOf(activityBehavior instanceof CallActivityBehavior);
        Boolean expanded = (Boolean)activity.getProperty("isExpanded");
        if(expanded != null) {
            collapsed = Boolean.valueOf(!expanded.booleanValue());
        }

        Boolean isInterrupting = null;
        if(activityBehavior instanceof BoundaryEventActivityBehavior) {
            isInterrupting = Boolean.valueOf(((BoundaryEventActivityBehavior)activityBehavior).isInterrupting());
        }

        Iterator nestedActivityArray = activity.getOutgoingTransitions().iterator();

        ArrayNode execution;
        while(nestedActivityArray.hasNext()) {
            PvmTransition properties = (PvmTransition)nestedActivityArray.next();
            String propertiesJSON = (String)properties.getProperty("name");
            boolean callActivityBehavior = highLightedFlows.contains(properties.getId());
            boolean nestedActivity = properties.getProperty("condition") != null && !((String)activity.getProperty("type")).toLowerCase().contains("gateway");
            boolean lastProcessDefinition = properties.getId().equals(activity.getProperty("default")) && ((String)activity.getProperty("type")).toLowerCase().contains("gateway");
            List processInstanceJSON = ((TransitionImpl)properties).getWaypoints();
            execution = (new ObjectMapper()).createArrayNode();
            ArrayNode processInstanceJSON1 = (new ObjectMapper()).createArrayNode();

            for(int errorEventDefinition = 0; errorEventDefinition < processInstanceJSON.size(); errorEventDefinition += 2) {
                execution.add((Integer)processInstanceJSON.get(errorEventDefinition));
                processInstanceJSON1.add((Integer)processInstanceJSON.get(errorEventDefinition + 1));
            }

            ObjectNode errorEventDefinition1 = (new ObjectMapper()).createObjectNode();
            errorEventDefinition1.put("id", properties.getId());
            errorEventDefinition1.put("name", propertiesJSON);
            errorEventDefinition1.put("flow", "(" + properties.getSource().getId() + ")--" + properties.getId() + "-->(" + properties.getDestination().getId() + ")");
            if(nestedActivity) {
                errorEventDefinition1.put("isConditional", nestedActivity);
            }

            if(lastProcessDefinition) {
                errorEventDefinition1.put("isDefault", lastProcessDefinition);
            }

            if(callActivityBehavior) {
                errorEventDefinition1.put("isHighLighted", callActivityBehavior);
            }

            errorEventDefinition1.put("xPointArray", execution);
            errorEventDefinition1.put("yPointArray", processInstanceJSON1);
            sequenceFlowArray.add(errorEventDefinition1);
        }

        ArrayNode nestedActivityArray1 = (new ObjectMapper()).createArrayNode();
        Iterator properties1 = activity.getActivities().iterator();

        while(properties1.hasNext()) {
            ActivityImpl propertiesJSON1 = (ActivityImpl)properties1.next();
            nestedActivityArray1.add(propertiesJSON1.getId());
        }

        Map properties2 = activity.getProperties();
        ObjectNode propertiesJSON2 = (new ObjectMapper()).createObjectNode();
        Iterator callActivityBehavior1 = properties2.keySet().iterator();

        while(true) {
            while(callActivityBehavior1.hasNext()) {
                String nestedActivity1 = (String)callActivityBehavior1.next();
                Object lastProcessDefinition1 = properties2.get(nestedActivity1);
                if(lastProcessDefinition1 instanceof String) {
                    propertiesJSON2.put(nestedActivity1, (String)properties2.get(nestedActivity1));
                } else if(lastProcessDefinition1 instanceof Integer) {
                    propertiesJSON2.put(nestedActivity1, (Integer)properties2.get(nestedActivity1));
                } else if(lastProcessDefinition1 instanceof Boolean) {
                    propertiesJSON2.put(nestedActivity1, (Boolean)properties2.get(nestedActivity1));
                } else if("initial".equals(nestedActivity1)) {
                    ActivityImpl processInstanceJSON3 = (ActivityImpl)properties2.get(nestedActivity1);
                    propertiesJSON2.put(nestedActivity1, processInstanceJSON3.getId());
                } else {
                    ObjectNode errorEventDefinitionJSON;
                    ArrayList processInstanceJSON2;
                    Iterator processInstanceJSON6;
                    if("timerDeclarations".equals(nestedActivity1)) {
                        processInstanceJSON2 = (ArrayList)properties2.get(nestedActivity1);
                        execution = (new ObjectMapper()).createArrayNode();
                        if(processInstanceJSON2 != null) {
                            processInstanceJSON6 = processInstanceJSON2.iterator();

                            while(processInstanceJSON6.hasNext()) {
                                TimerDeclarationImpl errorEventDefinition4 = (TimerDeclarationImpl)processInstanceJSON6.next();
                                errorEventDefinitionJSON = (new ObjectMapper()).createObjectNode();
                                errorEventDefinitionJSON.put("isExclusive", errorEventDefinition4.isExclusive());
                                if(errorEventDefinition4.getRepeat() != null) {
                                    errorEventDefinitionJSON.put("repeat", errorEventDefinition4.getRepeat());
                                }

                                errorEventDefinitionJSON.put("retries", String.valueOf(errorEventDefinition4.getRetries()));
                                errorEventDefinitionJSON.put("type", errorEventDefinition4.getJobHandlerType());
                                errorEventDefinitionJSON.put("configuration", errorEventDefinition4.getJobHandlerConfiguration());
                                execution.add(errorEventDefinitionJSON);
                            }
                        }

                        if(execution.size() > 0) {
                            propertiesJSON2.put(nestedActivity1, execution);
                        }
                    } else if("eventDefinitions".equals(nestedActivity1)) {
                        processInstanceJSON2 = (ArrayList)properties2.get(nestedActivity1);
                        execution = (new ObjectMapper()).createArrayNode();
                        if(processInstanceJSON2 != null) {
                            processInstanceJSON6 = processInstanceJSON2.iterator();

                            while(processInstanceJSON6.hasNext()) {
                                EventSubscriptionDeclaration errorEventDefinition3 = (EventSubscriptionDeclaration)processInstanceJSON6.next();
                                errorEventDefinitionJSON = (new ObjectMapper()).createObjectNode();
                                if(errorEventDefinition3.getActivityId() != null) {
                                    errorEventDefinitionJSON.put("activityId", errorEventDefinition3.getActivityId());
                                }

                                errorEventDefinitionJSON.put("eventName", errorEventDefinition3.getEventName());
                                errorEventDefinitionJSON.put("eventType", errorEventDefinition3.getEventType());
                                errorEventDefinitionJSON.put("isAsync", errorEventDefinition3.isAsync());
                                errorEventDefinitionJSON.put("isStartEvent", errorEventDefinition3.isStartEvent());
                                execution.add(errorEventDefinitionJSON);
                            }
                        }

                        if(execution.size() > 0) {
                            propertiesJSON2.put(nestedActivity1, execution);
                        }
                    } else if("errorEventDefinitions".equals(nestedActivity1)) {
                        processInstanceJSON2 = (ArrayList)properties2.get(nestedActivity1);
                        execution = (new ObjectMapper()).createArrayNode();
                        if(processInstanceJSON2 != null) {
                            processInstanceJSON6 = processInstanceJSON2.iterator();

                            while(processInstanceJSON6.hasNext()) {
                                ErrorEventDefinition errorEventDefinition2 = (ErrorEventDefinition)processInstanceJSON6.next();
                                errorEventDefinitionJSON = (new ObjectMapper()).createObjectNode();
                                if(errorEventDefinition2.getErrorCode() != null) {
                                    errorEventDefinitionJSON.put("errorCode", errorEventDefinition2.getErrorCode());
                                } else {
                                    errorEventDefinitionJSON.putNull("errorCode");
                                }

                                errorEventDefinitionJSON.put("handlerActivityId", errorEventDefinition2.getHandlerActivityId());
                                execution.add(errorEventDefinitionJSON);
                            }
                        }

                        if(execution.size() > 0) {
                            propertiesJSON2.put(nestedActivity1, execution);
                        }
                    }
                }
            }

            if("callActivity".equals(properties2.get("type"))) {
                CallActivityBehavior callActivityBehavior2 = null;
                if(activityBehavior instanceof CallActivityBehavior) {
                    callActivityBehavior2 = (CallActivityBehavior)activityBehavior;
                }

                if(callActivityBehavior2 != null) {
                    propertiesJSON2.put("processDefinitonKey", callActivityBehavior2.getProcessDefinitonKey());
                    ArrayNode nestedActivity2 = (new ObjectMapper()).createArrayNode();
                    if(processInstance != null) {
                        List lastProcessDefinition2 = this.runtimeService.createExecutionQuery().processInstanceId(processInstanceId).activityId(activity.getId()).list();
                        if(!lastProcessDefinition2.isEmpty()) {
                            Iterator processInstanceJSON4 = lastProcessDefinition2.iterator();

                            while(processInstanceJSON4.hasNext()) {
                                Execution execution1 = (Execution)processInstanceJSON4.next();
                                ObjectNode processInstanceJSON7 = (ObjectNode)subProcessInstanceMap.get(execution1.getId());
                                nestedActivity2.add(processInstanceJSON7);
                            }
                        }
                    }

                    if(nestedActivity2.size() == 0 && StringUtils.isNotEmpty(callActivityBehavior2.getProcessDefinitonKey())) {
                        ProcessDefinition lastProcessDefinition3 = (ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(callActivityBehavior2.getProcessDefinitonKey()).latestVersion().singleResult();
                        if(lastProcessDefinition3 != null) {
                            ObjectNode processInstanceJSON5 = (new ObjectMapper()).createObjectNode();
                            processInstanceJSON5.put("processDefinitionId", lastProcessDefinition3.getId());
                            processInstanceJSON5.put("processDefinitionKey", lastProcessDefinition3.getKey());
                            processInstanceJSON5.put("processDefinitionName", lastProcessDefinition3.getName());
                            nestedActivity2.add(processInstanceJSON5);
                        }
                    }

                    if(nestedActivity2.size() > 0) {
                        propertiesJSON2.put("processDefinitons", nestedActivity2);
                    }
                }
            }

            activityJSON.put("activityId", activity.getId());
            activityJSON.put("properties", propertiesJSON2);
            if(multiInstance != null) {
                activityJSON.put("multiInstance", multiInstance);
            }

            if(collapsed.booleanValue()) {
                activityJSON.put("collapsed", collapsed);
            }

            if(nestedActivityArray1.size() > 0) {
                activityJSON.put("nestedActivities", nestedActivityArray1);
            }

            if(isInterrupting != null) {
                activityJSON.put("isInterrupting", isInterrupting);
            }

            activityJSON.put("x", activity.getX());
            activityJSON.put("y", activity.getY());
            activityJSON.put("width", activity.getWidth());
            activityJSON.put("height", activity.getHeight());
            activityArray.add(activityJSON);
            callActivityBehavior1 = activity.getActivities().iterator();

            while(callActivityBehavior1.hasNext()) {
                ActivityImpl nestedActivity3 = (ActivityImpl)callActivityBehavior1.next();
                this.getActivity(processInstanceId, nestedActivity3, activityArray, sequenceFlowArray, processInstance, highLightedFlows, subProcessInstanceMap);
            }

            return;
        }
    }

    private JsonNode getProcessDefinitionResponse(ProcessDefinitionEntity processDefinition) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode pdrJSON = mapper.createObjectNode();
        pdrJSON.put("id", processDefinition.getId());
        pdrJSON.put("name", processDefinition.getName());
        pdrJSON.put("key", processDefinition.getKey());
        pdrJSON.put("version", processDefinition.getVersion());
        pdrJSON.put("deploymentId", processDefinition.getDeploymentId());
        pdrJSON.put("isGraphicNotationDefined", this.isGraphicNotationDefined(processDefinition));
        return pdrJSON;
    }

    private boolean isGraphicNotationDefined(ProcessDefinitionEntity processDefinition) {
        return ((ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(processDefinition.getId())).isGraphicalNotationDefined();
    }
}
