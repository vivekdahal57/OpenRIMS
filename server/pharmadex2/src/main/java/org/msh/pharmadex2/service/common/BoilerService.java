package org.msh.pharmadex2.service.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.msh.pdex2.dto.i18n.Language;
import org.msh.pdex2.dto.table.Headers;
import org.msh.pdex2.dto.table.TableCell;
import org.msh.pdex2.dto.table.TableHeader;
import org.msh.pdex2.dto.table.TableQtb;
import org.msh.pdex2.dto.table.TableRow;
import org.msh.pdex2.exception.ObjectNotFoundException;
import org.msh.pdex2.i18n.Messages;
import org.msh.pdex2.model.i18n.ResourceBundle;
import org.msh.pdex2.model.old.Query;
import org.msh.pdex2.model.old.User;
import org.msh.pdex2.model.r2.Assembly;
import org.msh.pdex2.model.r2.Concept;
import org.msh.pdex2.model.r2.EventLog;
import org.msh.pdex2.model.r2.FileResource;
import org.msh.pdex2.model.r2.History;
import org.msh.pdex2.model.r2.Register;
import org.msh.pdex2.model.r2.Scheduler;
import org.msh.pdex2.model.r2.Thing;
import org.msh.pdex2.model.r2.ThingDoc;
import org.msh.pdex2.model.r2.ThingPerson;
import org.msh.pdex2.model.r2.ThingRegister;
import org.msh.pdex2.model.r2.ThingScheduler;
import org.msh.pdex2.model.r2.ThingThing;
import org.msh.pdex2.repository.common.JdbcRepository;
import org.msh.pdex2.repository.common.QueryRepository;
import org.msh.pdex2.repository.r2.AssemblyRepo;
import org.msh.pdex2.repository.r2.ConceptRepo;
import org.msh.pdex2.repository.r2.EventLogRepo;
import org.msh.pdex2.repository.r2.FileResourceRepo;
import org.msh.pdex2.repository.r2.HistoryRepo;
import org.msh.pdex2.repository.r2.RegisterRepo;
import org.msh.pdex2.repository.r2.SchedulerRepo;
import org.msh.pdex2.repository.r2.ThingDocRepo;
import org.msh.pdex2.repository.r2.ThingPersonRepo;
import org.msh.pdex2.repository.r2.ThingRegisterRepo;
import org.msh.pdex2.repository.r2.ThingRepo;
import org.msh.pdex2.repository.r2.ThingSchedulerRepo;
import org.msh.pdex2.repository.r2.ThingThingRepo;
import org.msh.pdex2.services.r2.ClosureService;
import org.msh.pharmadex2.service.r2.LiteralService;
import org.msh.pharmadex2.service.r2.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.binodnme.dateconverter.converter.DateConverter;
import com.github.binodnme.dateconverter.utils.DateBS;

/**
 * Common utilities
 * @author alexk
 *
 */
@Service
public class BoilerService {
	private static final Logger logger = LoggerFactory.getLogger(BoilerService.class);
	@Autowired
	ConceptRepo conceptRepo;
	@Autowired
	Messages messages;
	@Autowired
	QueryRepository queryRepo;
	@Autowired
	ThingRepo thingRepo;
	@Autowired
	private ClosureService closureServ;
	@Autowired
	private LiteralService literalServ;
	@Autowired
	private HistoryRepo historyRepo;
	@Autowired
	private AssemblyRepo assmRepo;
	@Autowired
	private FileResourceRepo fileResRepo;
	@Autowired
	private EventLogRepo eventLogRepo;
	@Autowired
	private SchedulerRepo schedRepo;
	@Autowired
	private ThingSchedulerRepo thingSchedRepo;
	@Autowired
	private ThingRegisterRepo thingRegRepo;
	@Autowired
	private RegisterRepo registerRepo;
	@Autowired
	private JdbcRepository jdbcRepo;
	@Autowired
	private ThingDocRepo thingDocRepo;
	@Autowired
	private ThingThingRepo thingThingRepo;
	@Autowired
	private ThingPersonRepo thingPersonRepo;
	/**
	 * Convert resource bundle to DTO
	 * @param bundle
	 * @return
	 */
	@Transactional
	public Language resourceBundleToLanguage(ResourceBundle bundle) {
		Language ret = new Language();
		ret.setDisplayName(bundle.getDisplayName());
		ret.setFlag64("");
		ret.setFlagSVG(bundle.getSvgFlag());
		ret.setLocaleAsString(bundle.getLocale());
		return ret;
	}
	/**
	 * Full name of a user
	 * Maybe first and second names will be in the future
	 * @param user
	 * @return
	 */
	@Transactional
	public String getFullUserName(User user) {
		String ret=user.getName();
		return ret;
	}
	/**
	 * Convert local date time to old date
	 * @param dateToConvert
	 * @return
	 */
	public Date localDateTimeToDate(LocalDateTime dateToConvert) {
		return java.util.Date
				.from(dateToConvert.atZone(ZoneId.systemDefault())
						.toInstant());
	}
	/**
	 * local date to date
	 * @param dateToConvert
	 * @return
	 */
	public Date localDateToDate(LocalDate dateToConvert) {
		if(dateToConvert==null) {
			return new Date();
		}
		return java.util.Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}
	/**
	 * Date to local date
	 * @param dateToConvert
	 * @return now if parameter is null
	 */
	public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		if(dateToConvert== null) {
			return LocalDate.now();
		}
		return Instant.ofEpochMilli(dateToConvert.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
	/**
	 * Create display value for all headers
	 * @param headers
	 * @return
	 */
	public Headers translateHeaders(Headers headers) {
		for(TableHeader h :headers.getHeaders()) {
			h.setDisplayValue(messages.get(h.getValueKey()));
		}
		return headers;
	}

	/**
	 * Translate all i8 cells 
	 * @param rows
	 * @param rowKey
	 */
	public TableQtb translateRows(TableQtb table) {
		for(TableRow row : table.getRows()) {
			for(TableCell cell : row.getRow()) {
				String headKey= cell.getKey();
				TableHeader head = table.getHeaders().getHeaderByKey(headKey);
				if(head != null) {
					if(head.getColumnType()==TableHeader.COLUMN_I18 || head.getColumnType()==TableHeader.COLUMN_I18LINK) {
						if(cell.getOriginalValue() instanceof String) {
							String val = (String) cell.getOriginalValue();
							cell.setValue(messages.get(val));
						}else {
							cell.setValue("UNDEFINED");
						}
					}
				}
			}
		}
		return table;
	}

	/**
	 * Load SQL query from the database by key
	 * @param key
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public String loadQuery(String key) throws ObjectNotFoundException {
		Optional<Query> qo = queryRepo.findByKey(key);
		if(qo.isPresent()) {
			return qo.get().getSql();
		}else {
			throw new ObjectNotFoundException("Query not found. Key is "+key, logger);
		}
	}
	/**
	 * Load a thing by node or return new empty thing
	 * @param node
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Thing thingByNode(Concept node, Thing thing) {
		if(node.getID()>0) {
			List<Thing> retl= thingRepo.findByConcept(node);
			if(retl.size()>0) {
				thing = retl.get(0);
			}
		}
		return thing;
	}
	/**
	 * Load thing by node strict
	 * @param incl
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public Thing thingByNode(Concept node) throws ObjectNotFoundException {
		List<Thing> retl= thingRepo.findByConcept(node);
		if(retl.size()>0) {
			return retl.get(0);
		}
		throw new ObjectNotFoundException("loadThingByNode. Thing not found. Node id is " +node.getID());
	}


	/**
	 * Get all workflow activities by id of any activity
	 * @param activityId
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Map<String,List<Concept>> workflowActivities(long activityId) throws ObjectNotFoundException {
		Map<String, List<Concept>> ret = new LinkedHashMap<String, List<Concept>>();
		if(activityId>0) {
			Concept actNode=closureServ.loadConceptById(activityId);
			List<Concept> allParents = closureServ.loadParents(actNode);
			if(allParents.size()>2) {
				Concept initNode=allParents.get(2);
				Concept initiator=allParents.get(1);
				List<Concept> init = new ArrayList<Concept>();
				init.add(initNode);
				ret.put(initiator.getIdentifier(),init);
				List<Concept> executors=literalServ.loadOnlyChilds(initNode);
				for(Concept executor : executors) {
					List<Concept> activities = literalServ.loadOnlyChilds(executor);
					ret.put(executor.getIdentifier(), activities);
				}
			}
		}
		return ret;
	}
	/**
	 * Get history by history id
	 * @param historyId
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public History historyById(long historyId) throws ObjectNotFoundException {
		Optional<History> histo = historyRepo.findById(historyId);
		if(histo.isPresent()) {
			return histo.get();
		}else {
			throw new ObjectNotFoundException("History record not found. Id is "+historyId);
		}
	}
	/**
	 * GEt list of history records by a node of activity
	 * @param activityNode
	 * @return
	 */
	@Transactional
	public List<History> historyByActivityNode(Concept activityNode) {
		List<History> ret = new ArrayList<History>();
		ret = historyRepo.findAllByActivityOrderByCome(activityNode);
		return ret;
	}
	/**
	 * Save a history record
	 * @param history
	 * @return
	 */
	@Transactional
	public History saveHistory(History history) {
		return historyRepo.save(history);
	}
	/**
	 * Save a thing
	 * @param thing
	 * @return
	 */
	@Transactional
	public Thing saveThing(Thing thing) {
		return thingRepo.save(thing);
	}
	/**
	 * History by Application data - full history, order is natural - by ID -
	 * @param applicationData
	 * @return
	 */
	@Transactional
	public List<History> historyAll(Concept applicationData) {
		List<History> ret = historyRepo.findAllByApplicationDataOrderByID(applicationData);
		return ret;
	}

	/**
	 * load all history for application. Sort by Come
	 * @param application
	 * @return empty list if no
	 */
	@Transactional
	public List<History> historyAllByApplication(Concept application) {
		List<History> ret = historyRepo.findAllByApplicationOrderByCome(application);
		return ret;
	}
	/**
	 * Extract application or data url using appropriative node
	 * @param application
	 * @return url or exception
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public String url(Concept node) throws ObjectNotFoundException {
		String ret="";
		Concept exec = closureServ.getParent(node);
		if(exec!=null) {
			Concept root= closureServ.getParent(exec);
			if(root!=null) {
				return root.getIdentifier();
			}else {
				throw new ObjectNotFoundException("url. Wrong concept. ID is "+node.getID(),logger);
			}
		}else {
			throw new ObjectNotFoundException("url. Wrong concept. ID is "+node.getID(),logger);
		}
	}
	/**
	 * Load assembly record by the concept of variable
	 * @param var
	 * @param strict throws exception on not found, otherwise returns new Assembly
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Assembly assemblyByVariable(Concept var, boolean strict) throws ObjectNotFoundException {
		Optional<Assembly> asm = assmRepo.findByPropertyName(var);
		if(asm.isPresent()) {
			return asm.get();
		}else {
			if(strict) {
				throw new ObjectNotFoundException("assemblyByVariable. Assembly not found by vaiable. Variable ID is "+var.getID(),logger);
			}else {
				return new Assembly();
			}
		}
	}
	/**
	 * Save an assembly
	 * @param assm
	 * @return
	 */
	public Assembly assemblySave(Assembly assm) {
		return assmRepo.save(assm);
	}
	/**
	 * Load data configuration from assembly table
	 * Sorted by row, col, ord
	 * @param url
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public List<Assembly> loadDataConfiguration(String url) throws ObjectNotFoundException {
		List<Assembly> ret = new ArrayList<Assembly>();
		List<Concept> datas = loadAllDataConfigurations();
		List<Concept> vars = new ArrayList<Concept>();
		for(Concept dat : datas) {
			if(dat.getIdentifier().equalsIgnoreCase(url) && dat.getActive()) {
				List<Concept> varsAll = literalServ.loadOnlyChilds(dat);
				for(Concept var : varsAll) {
					if(var.getActive()) {
						vars.add(var);
					}
				}
			}
		}
		if(vars.size()>0) {
			ret=assmRepo.findAllByPropertyNameIn(vars, Sort.by(Sort.Direction.ASC,"row", "col", "ord"));
		}
		return ret;
	}
	/**
	 * Load a list of variables that configured under the URL
	 * @return
	 * @throws ObjectNotFoundException
	 */
	@Transactional
	public List<Concept> loadAllDataConfigurations() throws ObjectNotFoundException {
		Concept root = closureServ.loadRoot(SystemService.DATA_COLLECTIONS_ROOT);
		List<Concept> datas = literalServ.loadOnlyChilds(root);
		return datas;
	}
	/**
	 * Load file resource by node ID
	 * @param concept
	 * @return file resource, rise except if not found
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public FileResource fileResourceByNode(Concept node) throws ObjectNotFoundException {
		Optional<FileResource> freso = fileResRepo.findByConcept(node);
		if(freso.isPresent()) {
			return freso.get();
		}else {
			throw new ObjectNotFoundException("fileResourceByNode. Can't find file resource by node. Node Id is "+node.getID(),logger);
		}
	}
	/**
	 * GEt file resource by ID
	 * @param fileId
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public FileResource fileResourceById(long fileId) throws ObjectNotFoundException {
		Optional<FileResource> freso = fileResRepo.findById(fileId);
		if(freso.isPresent()) {
			return freso.get();
		}else {
			throw new ObjectNotFoundException("fileResourceById. File resource not found. Id is "+fileId,logger);
		}
	}
	/**
	 * Create local data from ISO string
	 * @param valStr
	 * @return
	 */
	public LocalDate localDateParse(String valStr) {
		LocalDate ld= LocalDate.now();
		try {
			ld = LocalDate.parse(valStr);
		} catch (Exception e) {
			logger.debug("*********** can't parse LocalDate ='"+ valStr+"'");
			ld = LocalDate.now();
		}
		return ld;
	}
	/**
	 * Pares a string to long
	 * @param valStr
	 * @return
	 */
	public Long longParse(String valStr) {
		Long ret = 0l;
		try {
			ret = Long.parseLong(valStr);
		} catch (NumberFormatException e) {
			logger.debug("*********** can't parse Long ='"+ valStr+"'");
			ret=0l;
		}
		return ret;
	}
	/**
	 * Add to eventlog cancel activity event
	 * @param activity
	 * @param email
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public void logCancelActvityEvent(History his, String email) throws ObjectNotFoundException {
		EventLog log = new EventLog();
		log.setConceptId(his.getApplication().getID());
		log.setEmail(email);
		log.setSource("ACTIVITY");
		log.setMessage("Activity has been cancelled. " + literalServ.readPrefLabel(his.getActConfig()));
		eventLogRepo.save(log);
	}
	/**
	 * Load scheduler by concept
	 * @param concept
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Scheduler schedulerByNode(Concept concept) throws ObjectNotFoundException {
		Optional<Scheduler> schedo = schedRepo.findByConcept(concept);
		if(schedo.isPresent()) {
			return schedo.get();
		}else {
			throw new ObjectNotFoundException("loadSchedulerByNode. Can`t load Scheduler by concept. Concept ID is "+concept.getID(),logger);
		}
	}

	/**
	 * Load scheduler by ID
	 * @param nodeId
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public Scheduler schedulerById(long id) throws ObjectNotFoundException {
		Optional<Scheduler> schedo = schedRepo.findById(id);
		if(schedo.isPresent()) {
			return schedo.get();
		}else {
			throw new ObjectNotFoundException("loadSchedulerByNode. Can`t load Scheduler by ID is "+id,logger);
		}
	}
	/**
	 * Save the schedule
	 * @param sch
	 * @return 
	 */
	public Scheduler saveSchedule(Scheduler sch) {
		return schedRepo.save(sch);
	}
	/**
	 * Load THingScheduler by ID
	 * @param id
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public ThingScheduler thingSchedulerById(long id) throws ObjectNotFoundException {
		Optional<ThingScheduler> tso = thingSchedRepo.findById(id);
		if(tso.isPresent()) {
			return tso.get();
		}else {
			throw new ObjectNotFoundException("ThingScheduler not found by ID="+id,logger);
		}
	}
	/**
	 * Get register record by concept of it
	 * @param concept
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Register registerByConcept(Concept concept) throws ObjectNotFoundException {
		Optional<Register> rego = registerRepo.findByConcept(concept);
		if(rego.isPresent()) {
			return rego.get();
		}else {
			throw new ObjectNotFoundException("registerByConcept. Register not found by concept. Concept ID is "+concept.getID(),logger);
		}
	}
	/**
	 * Save a register record
	 * @return
	 */
	@Transactional
	public Register saveRegister(Register entity) {
		return registerRepo.save(entity);
	}
	/**
	 * finf all registration records with regNum given
	 * @param regNum
	 * @param url 
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public List<Register> registerByUrlAndNumber(String regNum, String url) throws ObjectNotFoundException {
		Concept conc = closureServ.loadRoot(url);
		List<Register> ret = new ArrayList<Register>();
		ret = registerRepo.findAllByConceptAndRegister(conc,regNum.trim());
		return ret;
	}


	/**
	 * Convert the local date to Nepali date string
	 * @param dt
	 * @param full convert digit to nepali repr
	 * @return
	 */
	public String localDateToNepali(LocalDate ldt, boolean full) {
		/*List<Integer> nums = new ArrayList<Integer>();
		nums.add(0x966); // zero
		nums.add(0x967); // one
		nums.add(0x968); // two
		nums.add(0x969); // three
		nums.add(0x96A); // four
		nums.add(0x96B); // five
		nums.add(0x96C); // six
		nums.add(0x96D); // seven
		nums.add(0x96E); // eight
		nums.add(0x96F); // nine
		 */
		Date dt = localDateToDate(ldt);
		DateBS dateBS = DateConverter.convertADToBS(dt);
		String year = dateBS.getYear()+"";
		String month= dateBS.getMonth()+"";
		if(month.length()==1) {
			month="0"+month;
		}
		String day=dateBS.getDay()+"";
		if(day.length()==1) {
			day="0"+day;
		}
		day=day.substring(0,2);
		//convert to nepali chars
		StringBuilder sb = new StringBuilder();
		String str = year+"-"+month+"-"+day;
		str=str.trim();
		String ret=str;
		if(full) {
			/*	for (int i = 0; i < str.length(); i++) {
					String ch=str.substring(i, i+1);
					if(ch.length()==1) {
						if(ch.equals("-")) {
							sb.append("-");
						}else {
							Integer code= Integer.valueOf(ch);
							sb.append(Character.toChars(nums.get(code.intValue())));
						}
					}
				}
				ret  = sb.toString();*/
			ret=numberToNepali(str);
		}
		return ret;
	}
	/**
	 * Convert formatted number to Nepali characters
	 * @param str
	 * @return
	 */
	public String numberToNepali(String str) {
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(0x966); // zero
		nums.add(0x967); // one
		nums.add(0x968); // two
		nums.add(0x969); // three
		nums.add(0x96A); // four
		nums.add(0x96B); // five
		nums.add(0x96C); // six
		nums.add(0x96D); // seven
		nums.add(0x96E); // eight
		nums.add(0x96F); // nine
		String ret=str;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String ch=str.substring(i, i+1);
			if(ch.length()==1) {
				Integer code;
				try {
					code = Integer.valueOf(ch);
					Integer chCode=nums.get(code.intValue());
					if(chCode!=null) {
						sb.append(Character.toChars(chCode));
					}else {
						sb.append(ch);
					}
				} catch (NumberFormatException e) {
					sb.append(ch);
				}
			}
		}
		ret  = sb.toString();
		return ret;
	}

	/**
	 * GEt a histroy record by activity data
	 * @param conc
	 * @return
	 */
	public List<History> historyByActivitydata(Concept conc) {
		List<History> ret = historyRepo.findByActivityData(conc);
		return ret;
	}

	/**
	 * Save all selected rows in the table to a list
	 * @param table
	 * @return
	 */
	public List<Long> saveSelectedRows(TableQtb table) {
		List<Long> selected = new ArrayList<Long>();
		for(TableRow row : table.getRows()) {
			if(row.getSelected()) {
				selected.add(row.getDbID());
			}
		}
		return selected;
	}

	/**
	 * Restore selections in the table
	 * @param selected
	 * @param table
	 * @return
	 */
	public TableQtb selectedRowsRestore(List<Long> selected, TableQtb table) {
		if(selected.size()>0) {
			for(TableRow row :table.getRows()) {
				if(selected.contains(row.getDbID())) {
					row.setSelected(true);
				}else {
					row.setSelected(false);
				}
			}
		}
		return table;
	}

	/**
	 * Headers for person's selector table
	 * Here it is because of mutual use by services Resource and Amendment
	 * @param headers
	 * @return
	 */
	public Headers headersPersonSelector(Headers headers) {
		headers.getHeaders().clear();
		headers.getHeaders().add(TableHeader.instanceOf(
				"pref",
				"global_name",
				true,
				true,
				true,
				TableHeader.COLUMN_LINK,
				0));
		translateHeaders(headers);
		return headers;
	}
	/**
	 * Get all thing registers by concept
	 * @param concept
	 * @return ThingRet with ID==0 if not found
	 */
	@Transactional
	public List<ThingRegister> thingRegisterByNode(Concept concept) {
		List<ThingRegister> ret = thingRegRepo.findAllByConcept(concept);
		return ret;
	}
	/**
	 * Save a link to a documet
	 * @param ret
	 * @return
	 */
	@Transactional
	public ThingDoc saveThingDoc(ThingDoc ret) {
		ret = thingDocRepo.save(ret);
		return ret;
	}
	@Transactional
	public ThingDoc thingDocByNode(Concept docNode) throws ObjectNotFoundException {
		List<ThingDoc> tdol = thingDocRepo.findByConcept(docNode);
		if(tdol.size()>0) {
			return tdol.get(0);
		}else {
			throw new ObjectNotFoundException("ThingDocByNode. Can't find ThingDoc by concept. Id is "+docNode.getID());
		}
	}
	/**
	 * Home node of this node. Assumed that home and this nodes are existing
	 * @param node
	 * @return home node of the node
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Concept homeNode(Concept node) throws ObjectNotFoundException {
		List<ThingThing> ttList =thingThingRepo.findByConcept(node);
		if(ttList.size()>0) {
			Thing thing = thingByThingThing(ttList.get(0), true);
			return thing.getConcept();
		}else {
			return node;
		}
	}
	/**
	 * Load all thingdoc by url
	 * @param url
	 * @return
	 */
	@Transactional
	public List<ThingDoc> thingDocsByUrl(String url) {
		List<ThingDoc> retl = thingDocRepo.findByDocUrl(url);
		return retl;
	}
	/**
	 * Find thing by ThingDoc
	 * @param td
	 * @param exception
	 * @return thing with ID==0 if not found or exception
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public Thing thingByThingDoc(ThingDoc td, boolean exception) throws ObjectNotFoundException {
		Optional<Thing> thingo = thingRepo.findByDocuments(td);
		if(thingo.isPresent()) {
			return thingo.get();
		}else {
			if(exception) {
				throw new ObjectNotFoundException("loadThingByThingDoc. Thing not found, ThingDoc ID is "+td.getID(),logger);
			}else {
				return new Thing();
			}
		}
	}
	/**
	 * Thing doc by file node
	 * @param node
	 * @return new ThingDoc if not found
	 */
	@Transactional
	public ThingDoc loadThingDocByFileNode(Concept node) {
		List<ThingDoc> tdl = thingDocRepo.findByConcept(node);
		if(tdl.size()>0) {
			return tdl.get(0);
		}else {
			return new ThingDoc();
		}
	}
	/**
	 * Full Nepali years from ld to the current
	 * @param ld
	 * @return
	 */
	public int fullYearsNepali(LocalDate ld) {
		int years=0;
		DateBS nowBs = DateConverter.convertADToBS(new Date());
		DateBS ldBs = DateConverter.convertADToBS(localDateToDate(ld));
		int monthsNow = nowBs.getYear()*12+ldBs.getMonth();
		int monthsLd = ldBs.getYear()*12+ldBs.getMonth();
		int monthDif=monthsNow-monthsLd;	//months between
		years=monthDif/12; //full years between
		int rem =monthDif % 12;	//additional months
		if(rem==0) {						//the same month
			if(years>0) {
				if(nowBs.getDay()<=ldBs.getDay()) {	//not full year, compare to the date in the past
					years--;
				}
			}
			if(years<0) {
				if(nowBs.getDay()>ldBs.getDay()) {	//not full year compare to the date in the future
					years++;
				}
			}
		}
		return years;
	}
	/**
	 * Load ThingThing by it's concept. Mainly to determine variable name or root node itself
	 * @param dataNode
	 * @param strict - raise an exception, otherwise return null if not fount
	 * @return exception or null, if not found
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public ThingThing thingThing(Concept dataNode, boolean strict) throws ObjectNotFoundException {
		if(dataNode!=null) {
			List<ThingThing> retl = thingThingRepo.findByConcept(dataNode);
			if(retl.size()>0) {
				return retl.get(0);
			}else {
				if(strict) {
					throw new ObjectNotFoundException("thingthing by concept not found. Concept ID is "+dataNode.getID(),logger);
				}else {
					return null;
				}
			}
		}else {
			if(strict) {
				throw new ObjectNotFoundException("thingthing by concept not found. Concept is NULL",logger);
			}else {
				return null;
			}
		}
	}
	/**
	 * Load a Thing to which ThingThing linked to
	 * @param tt
	 * @param strict false - return null, true - raise an exception
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public Thing thingByThingThing(ThingThing tt, boolean strict) throws ObjectNotFoundException {
		if(tt!=null) {
			List<Thing> retl = thingRepo.findByThings(tt);
			if(retl!=null && retl.size()>0) {
				return retl.get(0);
			}else {
				if(strict) {
					throw new ObjectNotFoundException("ThingByThingThing. Thing not found. ID is "+tt.getID(), logger);
				}else {
					return null;
				}
			}
		}else {
			if(strict) {
				throw new ObjectNotFoundException("ThingByThingThing. ThingThing is null", logger);
			}else {
				return null;
			}
		}
	}
	/**
	 * Load ThingPerson by concept
	 * @param concept
	 * @param strict
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public ThingPerson thingPerson(Concept concept, boolean strict) throws ObjectNotFoundException {
		if(concept!=null) {
			List<ThingPerson> retl = thingPersonRepo.findByConcept(concept);
			if(retl!=null && retl.size()>0) {
				return retl.get(0);
			}else {
				if(strict) {
					throw new ObjectNotFoundException("ThingPerson. ThingPerson not found. Concept ID is "+concept.getID(), logger);
				}else {
					return null;
				}
			}
		}else {
			if(strict) {
				throw new ObjectNotFoundException("ThingPerson. Concept is null", logger);
			}else {
				return null;
			}
		}
	}
	
	/**
	 * Load thing by ThingPerson
	 * @param tp
	 * @param strict
	 * @return
	 * @throws ObjectNotFoundException 
	 */
	public Thing thingByThingPerson(ThingPerson tp, boolean strict) throws ObjectNotFoundException {
		if(tp!=null) {
			List<Thing> retl=thingRepo.findByPersons(tp);
			if(retl!=null && retl.size()>0) {
				return retl.get(0);
			}else {
				if(strict) {
					throw new ObjectNotFoundException("ThingByThingPerson. Thing not found. ThingPerson ID is "+tp.getID(), logger);
				}else {
					return null;
				}
			}
		}else {
			if(strict) {
				throw new ObjectNotFoundException("ThingByThingPerson. ThingPerson is null", logger);
			}else {
				return null;
			}
		}
	}

	/**
	 * Get a list of schedulers that suit the parameters
	 * @param id
	 * @param dataUrl
	 * @return list that may be empty
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public List<Scheduler> schedulerList(String dataUrl, long id) throws ObjectNotFoundException {
		List<Scheduler> ret = new ArrayList<Scheduler>();
		jdbcRepo.scheduler_list(dataUrl, id);
		List<TableRow> rows = jdbcRepo.qtbGroupReport("select * from scheduler_list", "", "", new Headers());
		for(TableRow row : rows) {
			ret.add(schedulerById(row.getDbID()));
		}
		return ret;
	}
	/**
	 * Load all activities that suits application url and application data
	 * @param applUrl
	 * @param applData
	 * @return empty list if not found
	 * @throws ObjectNotFoundException 
	 */
	@Transactional
	public List<History> activities(String applUrl, Concept applData) throws ObjectNotFoundException {
		List<History> ret = new ArrayList<History>();
		jdbcRepo.application_list(applUrl,applData.getID());
		Headers headers = new Headers();
		headers.setPageSize(Integer.MAX_VALUE);
		List<TableRow> rows = jdbcRepo.qtbGroupReport("select * from application_list", "", "", headers);
		for(TableRow row : rows) {
			ret.add(historyById(row.getDbID()));
		}
		return ret;
	}
}
