package org.palladiosimulator.measurementsui.wizardmain.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.palladiosimulator.edp2.models.Repository.RepositoryFactory;
import org.palladiosimulator.edp2.models.Repository.impl.RepositoryFactoryImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.impl.AssemblyContextImpl;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.impl.OperationProvidedRoleImpl;
import org.palladiosimulator.pcm.repository.impl.OperationRequiredRoleImpl;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.impl.ResourceDemandingSEFFImpl;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

public class MeasuringPointsContentProvider implements ITreeContentProvider{

    Object[] models;
    DataApplication da = DataApplication.getInstance();
    @Override
    public Object[] getElements(Object inputElement) {
//    	RepositoryFactory rf = new RepositoryFactoryImpl();
//    	
//        List<ResourceEnvironment> re = da.getModelAccessor().getResourceEnvironment();
//        List<org.palladiosimulator.pcm.system.System> sys = da.getModelAccessor().getSystem();
//        List<Allocation> all = da.getModelAccessor().getAllocation();
//        List<Repository> repo = da.getModelAccessor().getRepository();
//        List<UsageModel> um = da.getModelAccessor().getUsageModel();
//        List<Object> allmodels = new ArrayList<>();
//        allmodels.addAll(re);
//        allmodels.addAll(sys);
//        allmodels.addAll(all);
//        allmodels.addAll(repo);
//        allmodels.addAll(um);
//        allmodels.add(getAssemblyContexts());
//        System.out.println("testestest " +allmodels.size());
        return getAllObjects();
        
        
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getParent(Object element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        // TODO Auto-generated method stub
        return false;
    }

    public List<AssemblyContext> getAssemblyContexts() {
    	List<AssemblyContext> assemblyContexts = da.getModelAccessor().getSystem().stream().flatMap(e -> e.getAssemblyContexts__ComposedStructure().stream()).collect(Collectors.toList());
    	for(int i=0; i<assemblyContexts.size(); i++) {
    		System.out.println(assemblyContexts.get(i).getEntityName());
    	}
    	System.out.println("encap "+assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().getEntityName());
    	for(int j=0; j<assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().eContents().size();j++) {
    		System.out.println("Show me everything "+assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().eContents().get(j).toString());
    	}
    	System.out.println("one "+((OperationProvidedRoleImpl)assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().eContents().get(0)).getProvidingEntity_ProvidedRole().getProvidedRoles_InterfaceProvidingEntity().get(0).getEntityName());
//    	System.out.println("one moore"+((OperationRequiredRoleImpl)assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().eContents().get(0)).getRequiredInterface__OperationRequiredRole().getEntityName());

    	System.out.println("another one"+((ResourceDemandingSEFFImpl)assemblyContexts.get(0).getEncapsulatedComponent__AssemblyContext().eContents().get(2)).getDescribedService__SEFF().getEntityName());
    	System.out.println(assemblyContexts.getClass());
    	return assemblyContexts;
    }
    
    public List<ResourceContainer> getResourceContainer() {
    	List<ResourceContainer> resourceContainer = da.getModelAccessor().getResourceEnvironment().stream().flatMap(e -> e.getResourceContainer_ResourceEnvironment().stream()).collect(Collectors.toList());
    	for(int i=0; i<resourceContainer.size(); i++) {
    		System.out.println(resourceContainer.get(i).getEntityName());
    	}
    	
    	return resourceContainer;
    }
    
    public List<ProcessingResourceSpecification> getActiveResources() {
    	
    	List<ProcessingResourceSpecification> activeresources = getResourceContainer().stream().flatMap(e -> e.getActiveResourceSpecifications_ResourceContainer().stream()).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		System.out.println("resources "+activeresources.get(i).getActiveResourceType_ActiveResourceSpecification().getEntityName());
    	}
    	
    	return activeresources;
    }
    public List<LinkingResource> getLinkingResources() {
    	
    	List<LinkingResource> activeresources = da.getModelAccessor().getResourceEnvironment().stream().flatMap(e -> e.getLinkingResources__ResourceEnvironment().stream()).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		System.out.println("resources "+activeresources.get(i).getEntityName());
    	}
    	
    	return activeresources;
    }
    
    public List<UsageScenario> getUsageScenarios() {
    	
    	List<UsageScenario> activeresources = da.getModelAccessor().getUsageModel().stream().flatMap(e -> e.getUsageScenario_UsageModel().stream()).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		System.out.println("resources "+activeresources.get(i).getEntityName());
    	}
    	
    	return activeresources;
    }
    
    public List<AbstractUserAction> getEntryLevelSystemCalls() {
    	
    	List<AbstractUserAction> activeresources = getUsageScenarios().stream().flatMap(e -> e.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().stream()).filter(e->e instanceof EntryLevelSystemCall).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		System.out.println("resources "+activeresources.get(i).getEntityName());
    	}
    	
    	return activeresources;
    }
    public List<EObject> getExternalCallActions() {
    	
//    	List<EObject> activeresources = getSEFFs().stream().flatMap(e -> e.eContents().stream()).filter(e->e instanceof ExternalCallAction).collect(Collectors.toList());
//    	for(int i=0; i<activeresources.size(); i++) {
//    		System.out.println("resources2  "+(activeresources.get(i).toString()));
//    	}
    	List<EObject> activeresources = getSeffs().stream().flatMap(e -> e.eContents().stream()).filter(e->e instanceof ExternalCallAction).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		System.out.println("resources2  "+(activeresources.get(i).toString()));
    	}
    	return activeresources;
    }
    
public List<EObject> getComponents() {
	
    	System.out.println("ttestgroesse "+da.getModelAccessor().getRepository().get(0).eContents().size() );
      	for(int i=0; i<da.getModelAccessor().getRepository().get(0).eContents().size(); i++) {
    		
    		System.out.println("huui1"+da.getModelAccessor().getRepository().get(0).eContents().get(i).toString());
    	}
    	List<EObject> activeresources = da.getModelAccessor().getRepository().stream().flatMap(e -> e.eContents().stream()).filter(e->e instanceof BasicComponent).collect(Collectors.toList());
    	for(int i=0; i<activeresources.size(); i++) {
    		;
    		System.out.println("huui"+activeresources.get(i).toString());
    	}
    	
    	return activeresources;
    }

public List<EObject> getSeffs() {
	
	System.out.println("ttestgroesse "+da.getModelAccessor().getRepository().get(0).eContents().size() );
  	for(int i=0; i<da.getModelAccessor().getRepository().get(0).eContents().size(); i++) {
		
		System.out.println("huui1"+da.getModelAccessor().getRepository().get(0).eContents().get(i).toString());
	}
	List<EObject> activeresources = getComponents().stream().flatMap(e -> e.eContents().stream()).filter(e->e instanceof ResourceDemandingSEFF).collect(Collectors.toList());
	for(int i=0; i<activeresources.size(); i++) {
		;
		System.out.println("huui"+activeresources.get(i).toString());
	}
	
	return activeresources;
}
    
    public Object[] getAllObjects() {
        DataApplication da = DataApplication.getInstance();

        List<ResourceEnvironment> re = da.getModelAccessor().getResourceEnvironment();
        List<org.palladiosimulator.pcm.system.System> sys = da.getModelAccessor().getSystem();
        List<Allocation> all = da.getModelAccessor().getAllocation();
        List<Repository> repo = da.getModelAccessor().getRepository();
        List<UsageModel> um = da.getModelAccessor().getUsageModel();
        List<SubSystem> sub = da.getModelAccessor().getSubSystem();
        System.out.println("subsystem "+ sub.get(0));
        List<Object> allmodels = new ArrayList<>();
        allmodels.addAll(re);
        allmodels.addAll(sys);
        allmodels.addAll(sub);
//        allmodels.addAll(all);
//        allmodels.addAll(repo);
//        allmodels.addAll(um);
        allmodels.addAll(getAssemblyContexts());
        allmodels.addAll(getResourceContainer());
        allmodels.addAll(getActiveResources()); 
        allmodels.addAll(getLinkingResources());
        allmodels.addAll(getUsageScenarios());
        allmodels.addAll(getEntryLevelSystemCalls());
        allmodels.addAll(getExternalCallActions());
        for(int i=0; i<allmodels.size(); i++) {
    		System.out.println("lol " +allmodels.get(i).toString());
    	}
        System.out.println("testestest " +allmodels.size());
        return allmodels.toArray();
    }

}
