package org.msh.pharmadex2.controller.r2;

import org.msh.pdex2.exception.ObjectNotFoundException;
import org.msh.pdex2.i18n.Messages;
import org.msh.pharmadex2.dto.ContentDTO;
import org.msh.pharmadex2.dto.DataCollectionDTO;
import org.msh.pharmadex2.dto.DataConfigDTO;
import org.msh.pharmadex2.dto.DataPreviewDTO;
import org.msh.pharmadex2.dto.DataVariableDTO;
import org.msh.pharmadex2.dto.Dict2DTO;
import org.msh.pharmadex2.dto.DictNodeDTO;
import org.msh.pharmadex2.dto.DictionariesDTO;
import org.msh.pharmadex2.dto.DictionaryDTO;
import org.msh.pharmadex2.dto.MessageDTO;
import org.msh.pharmadex2.dto.PublicOrgDTO;
import org.msh.pharmadex2.dto.ResourceDTO;
import org.msh.pharmadex2.dto.RootNodeDTO;
import org.msh.pharmadex2.dto.ThingDTO;
import org.msh.pharmadex2.dto.TilesDTO;
import org.msh.pharmadex2.dto.UserElementDTO;
import org.msh.pharmadex2.dto.WorkflowDTO;
import org.msh.pharmadex2.exception.DataNotFoundException;
import org.msh.pharmadex2.service.common.UserService;
import org.msh.pharmadex2.service.r2.ApplicationService;
import org.msh.pharmadex2.service.r2.ContentService;
import org.msh.pharmadex2.service.r2.DictService;
import org.msh.pharmadex2.service.r2.PubOrgService;
import org.msh.pharmadex2.service.r2.SupervisorService;
import org.msh.pharmadex2.service.r2.SystemService;
import org.msh.pharmadex2.service.r2.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common API for all authenticated users
 * @author alexk
 *
 */
@RestController
public class AdminAPI {
	@Autowired
	UserService userService;
	@Autowired
	ContentService contentService;
	@Autowired
	SupervisorService superVisServ;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private PubOrgService orgServ;
	@Autowired
	DictService dictServ;
	@Autowired
	ContentService сontentService;
	@Autowired
	ApplicationService applService;
	@Autowired
	ThingService thingService;
	@Autowired
	Messages messages;
	@Autowired
	private SystemService systemServ;
	

	/**
	 * Tiles for landing page
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 */
	@PostMapping("/api/admin/content")
	public ContentDTO adminContent(@RequestBody ContentDTO data) throws DataNotFoundException {
		try {
			data=contentService.loadContent(data, "admin");
			if(data.getTiles().size()==0) {
				data=contentService.adminTile(data);
			}
			return data;
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
	}


	/**
	 * Load the dictionary element
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 */
	@PostMapping("/api/admin/organization/load")
	public PublicOrgDTO organizationLoad(@RequestBody PublicOrgDTO data) throws DataNotFoundException {
		try {
			data = orgServ.loadByConcept(data.getNode());
			return data;
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
	}

	@PostMapping("/api/admin/organization/save")
	public PublicOrgDTO organizationSave(@RequestBody PublicOrgDTO data) throws DataNotFoundException {
		try {
			data = orgServ.save(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Make an organization inactive for selection
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/organization/suspend")
	public PublicOrgDTO organizationDelete(@RequestBody PublicOrgDTO data) throws DataNotFoundException {
		try {
			data = orgServ.suspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	@PostMapping("/api/admin/list/users")
	public UserElementDTO listUsers(@RequestBody UserElementDTO data) throws DataNotFoundException {
		try {
			data=userService.listUsers(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	@PostMapping("/api/admin/user/load")
	public UserElementDTO userLoad(@RequestBody UserElementDTO data) throws DataNotFoundException {
		try {
			data=userService.userLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	@PostMapping("/api/admin/user/save")
	public UserElementDTO userSave(@RequestBody UserElementDTO data) throws DataNotFoundException {
		try {
			data=userService.userSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * unlink a user from organization. Mainly to link to another organization
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/user/suspend")
	public UserElementDTO userSuspend(@RequestBody UserElementDTO data) throws DataNotFoundException {
		try {
			data=userService.userSuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Save a node
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/dictionary/node/save")
	public DictNodeDTO dictionaryNodeSave(@RequestBody DictNodeDTO data) throws DataNotFoundException {
		try {
			data=dictServ.save(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Save a node
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/dictionary/node/suspend")
	public DictNodeDTO dictionaryNodeSuspend(@RequestBody DictNodeDTO data) throws DataNotFoundException {
		try {
			data=dictServ.nodeSuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	/**
	 * All dicts
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/dictionary/all")
	public DictionariesDTO dictionaryAll(@RequestBody DictionariesDTO data) throws DataNotFoundException {
		try {
			data=dictServ.all(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	/**
	 * Load a node
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/root/node/load")
	public RootNodeDTO rootNodeLoad(@RequestBody RootNodeDTO data) throws DataNotFoundException {
		try {
			data=dictServ.rootNode(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	/**
	 * Save a node
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/root/node/save")
	public RootNodeDTO rootNodeSave(@RequestBody RootNodeDTO data) throws DataNotFoundException {
		try {
			data=dictServ.rootNodeSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	@PostMapping("/api/admin/tiles")
	public TilesDTO tilesLoad(Authentication auth, @RequestBody TilesDTO data) throws DataNotFoundException {
		try {
			data = сontentService.buildTiles(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	@PostMapping("/api/admin/tiles/update")
	public TilesDTO tilesUpdate(Authentication auth, @RequestBody TilesDTO data) throws DataNotFoundException {
		try {
			data = сontentService.updateTiles(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	@PostMapping("/api/admin/tiles/save")
	public TilesDTO tilesSave(Authentication auth, @RequestBody TilesDTO data) throws DataNotFoundException {
		try {
			data = сontentService.saveTiles(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	/**
	 * load "dictionary.guest.applications"
	 * @param auth
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/stages/workflow")
	public Dict2DTO stagesWorkflow(@RequestBody Dict2DTO data) throws DataNotFoundException {
		try {
			data= systemServ.stagesWorkflow(data);
			return data;
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
	}

	/**
	 * load/create configuration for workflow for known dictNodeId
	 * @param auth
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/workflow/configuration/load")
	public WorkflowDTO workflowConfigurationLoad(@RequestBody WorkflowDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.workflowConfiguration(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}

	/**
	 * Load activity or user data configuration
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/thing/load")
	public ThingDTO thingLoad(@RequestBody ThingDTO data) throws DataNotFoundException {
		if(data.getNodeId()>0) {
			try {
				data=superVisServ.thingLoad(data);
			} catch (ObjectNotFoundException e) {
				throw new DataNotFoundException(e);
			}
		}
		return data;
	}


	/**
	 * Append an new created activity to workflow to the end of path
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/workflow/activity/add")
	public WorkflowDTO workflowActivityAdd(@RequestBody WorkflowDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.workflowActivityAdd(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Suspend an activity and remove from the path
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/workflow/activity/suspend")
	public WorkflowDTO workflowActivitySuspend(@RequestBody WorkflowDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.workflowActivitySuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Load table with data collections
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collections/load")
	public DataConfigDTO dataCollectionsLoad(@RequestBody DataConfigDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionsLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Load a definition of a data collection
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collection/definition/load")
	public DataCollectionDTO dataCollectionDefinitionLoad(@RequestBody DataCollectionDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionDefinitionLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Save a definition of a data collection
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collection/definition/save")
	public DataCollectionDTO dataCollectionDefinitionSave(@RequestBody DataCollectionDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionDefinitionSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Suspend a data storage (will not be used)
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collection/definition/suspend")
	public DataCollectionDTO dataCollectionDefinitionSuspend(@RequestBody DataCollectionDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionDefinitionSuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Suspend a data storage (will not be used)
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collection/definition/duplicate")
	public DataCollectionDTO dataCollectionDefinitionDuplicate(@RequestBody DataCollectionDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionDefinitionDuplicate(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Load structure and screen layout for a data collection given  
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/collection/variables/load")
	public DataConfigDTO dataCollectionVariablesLoad(@RequestBody DataConfigDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionVariablesLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	/**
	 * Load a variable
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/configuration/variable/load")
	public DataVariableDTO dataCollectionVariableLoad(@RequestBody DataVariableDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionVariableLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Save a variable definition
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/configuration/variable/save")
	public DataVariableDTO dataCollectionVariableSave(@RequestBody DataVariableDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionVariableSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Suspend a variable definition
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/data/configuration/variable/suspend")
	public DataVariableDTO dataCollectionVariableSuspend(@RequestBody DataVariableDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionVariableSuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	@PostMapping("/api/admin/data/collection/definition/preview")
	public DataPreviewDTO dataCollectionDefinitionPreview(@RequestBody DataPreviewDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.dataCollectionDefinitionPreview(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Load all messages from table resource_message
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 */
	@PostMapping("/api/admin/messages/load")
	public MessageDTO messagesLoad(@RequestBody MessageDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.messagesLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
		
	}
	
	@PostMapping("/api/admin/messages/save")
	public MessageDTO messagesSave(@RequestBody MessageDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.messagesSave(data);
			
			// reload messages
			messages.getMessages().clear();
			messages.loadLanguages();
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
		
	}
	
	@PostMapping("/api/admin/reloadmessages")
	public String reloadMessages(@RequestBody String data) throws DataNotFoundException {
		messages.getMessages().clear();
		messages.loadLanguages();
		return data;
	}
	/**
	 * Responsible for a list of resources
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 */
	@PostMapping("/api/admin/resources/load")
	public ResourceDTO resourcesLoad(@RequestBody ResourceDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.resourcesLoad(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
		
	}
	/**
	 * Responsible for save a resource definition to the database
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 * @throws ObjectNotFoundException 
	 */
	@PostMapping("/api/admin/resource/definition/save")
	public ResourceDTO resurceDefinitionSave(@RequestBody ResourceDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.resourceDefinitionSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Responsible for suspend the resource definition
	 * @param data
	 * @return
	 * @throws DataNotFoundException 
	 * @throws ObjectNotFoundException 
	 */
	@PostMapping("/api/admin/resource/definition/suspend")
	public ResourceDTO resurceDefinitionSuspend(@RequestBody ResourceDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.resourceDefinitionSuspend(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	/**
	 * Prepare thing in accordance with the resource selected
	* url and nodeId
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/resource/thing/prepare")
	public ThingDTO resourceThingPrepare(@RequestBody ResourceDTO data) throws DataNotFoundException {
		try {
			ThingDTO ret=superVisServ.resourceThingPrepare(data);
			return ret;
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
	}
	/**
	 * Save a thing represent a resource
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/resource/save")
	public ThingDTO resourceSave(@RequestBody ThingDTO data) throws DataNotFoundException {
		try {
			data=superVisServ.resourceSave(data);
		} catch (ObjectNotFoundException e) {
			throw new DataNotFoundException(e);
		}
		return data;
	}
	
	
	/**
	 * Load the system dictionary "Actions"
	 * @param data
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/api/admin/processes/actions")
	public DictionaryDTO processesActions(@RequestBody DictionaryDTO data) throws DataNotFoundException {
			try {
				data=systemServ.submitActionDictionary();
				return data;
			} catch (ObjectNotFoundException e) {
				throw new DataNotFoundException(e);
			}
	}
}