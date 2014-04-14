package fr.hardcoding.svn.hooktools.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.hardcoding.svn.hooktools.HookTools;
import fr.hardcoding.svn.hooktools.action.AbstractAction;
import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.condition.resource.ResourceCondition;
import fr.hardcoding.svn.hooktools.condition.resource.filter.AbstractResourceFilter;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.HookType;

/**
 * This class represents a rule set for a hook.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class RuleSet {
	/*
	 * Binding related.
	 */
	/** The condition binding map. */
	private static final Map<String, Class<? extends AbstractCondition>> conditionBindings = new HashMap<>();
	/** The resource filter binding map. */
	private static final Map<String, Class<? extends AbstractResourceFilter>> resourceFilterBindings = new HashMap<>();
	/** The action binding map. */
	private static final Map<String, Class<? extends AbstractAction>> actionBindings = new HashMap<>();
	/*
	 * Binding name prefixes.
	 */
	/** The condition prefix. */
	private static final String CONDITION_PREFIX = "condition.";
	/** The resource filter prefix. */
	private static final String RESOURCE_FILTER_PREFIX = "resourceFilter.";
	/** The action prefix. */
	private static final String ACTION_PREFIX = "action.";
	/*
	 * Node names.
	 */
	/** The action node name. */
	private static final String ACTION_NODE_NAME = "action";
	/** The condition node name. */
	private static final String CONDITION_NODE_NAME = "condition";
	/** The filter node name. */
	private static final String FILTER_NODE_NAME = "filter";
	/** The parameter node name. */
	private static final String PARAMETER_NODE_NAME = "parameter";
	/*
	 * Rule set related.
	 */
	/** The rule collection. */
	private final List<Rule> rules;

	/*
	 * Load bindings.
	 */
	static {
		try (FileInputStream bindingsInputStream = new FileInputStream("F:/Programmation/Java/svnhooktools/config/bindings.properties")) {
			// Load binding properties from configuration file
			Properties properties = new Properties();
			properties.load(bindingsInputStream);
			// Parse each binding
			for (Object key : properties.keySet()) {
				// Get binding and class names
				String bindingName = (String) key;
				String className = properties.getProperty(bindingName);
				// Check binding type
				if (bindingName.startsWith(RuleSet.CONDITION_PREFIX)) {
					// Get condition name
					String conditionName = bindingName.substring(RuleSet.CONDITION_PREFIX.length());
					try {
						// Load class descriptor
						Class<?> clazz = Class.forName(className);
						// Check condition class
						if (!AbstractCondition.class.isAssignableFrom(clazz)) {
							HookTools.LOGGER.warning("The condition \""+className+"\" could not be loaded as valid condition class.");
							continue;
						}
						// Add condition class in the bindings
						RuleSet.conditionBindings.put(conditionName, clazz.asSubclass(AbstractCondition.class));
					} catch (ClassNotFoundException exception) {
						HookTools.LOGGER.log(Level.WARNING, "The class \""+className+"\" could not be loaded.");
					}
				} else if (bindingName.startsWith(RuleSet.RESOURCE_FILTER_PREFIX)) {
					// Get filter name
					String filterName = bindingName.substring(RuleSet.RESOURCE_FILTER_PREFIX.length());
					try {
						// Load class descriptor
						Class<?> clazz = Class.forName(className);
						// Check filter class
						if (!AbstractResourceFilter.class.isAssignableFrom(clazz)) {
							HookTools.LOGGER.warning("The filter \""+className+"\" could not be loaded as valid resource condition filter class.");
							continue;
						}
						// Add filter class in the bindings
						RuleSet.resourceFilterBindings.put(filterName, clazz.asSubclass(AbstractResourceFilter.class));
					} catch (ClassNotFoundException exception) {
						HookTools.LOGGER.log(Level.WARNING, "The class \""+className+"\" could not be loaded.");
					}
				} else if (bindingName.startsWith(RuleSet.ACTION_PREFIX)) {
					// Get action name
					String actionName = bindingName.substring(RuleSet.ACTION_PREFIX.length());
					try {
						// Load class descriptor
						Class<?> clazz = Class.forName(className);
						// Check action class
						if (!AbstractAction.class.isAssignableFrom(clazz)) {
							HookTools.LOGGER.warning("The action \""+className+"\" could not be loaded as valid action class.");
							continue;
						}
						// Add condition class in the bindings
						RuleSet.actionBindings.put(actionName, clazz.asSubclass(AbstractAction.class));
					} catch (ClassNotFoundException exception) {
						HookTools.LOGGER.log(Level.WARNING, "The class \""+className+"\" could not be loaded.");
					}
				}
			}
		} catch (FileNotFoundException exception) {
			// Log and exit
			HookTools.LOGGER.log(Level.SEVERE, "The bindings configuration file could not be found.", exception);
			System.exit(-1);
		} catch (IOException exception) {
			// Log and exit
			HookTools.LOGGER.log(Level.SEVERE, "The bindings configuration file could not be found.", exception);
			System.exit(-1);
		}
	}

	/**
	 * Create rule set from hook type.
	 * 
	 * @param hookType
	 *            The hook type to create rule set.
	 * @return The related rule set.
	 */
	public static RuleSet fromHookType(HookType hookType) {
		// Create rule set
		RuleSet ruleSet = new RuleSet();
		// Get stream from configuration file
		// TODO fix configuration path
		try (InputStream inputStream = new FileInputStream("F:/Programmation/Java/svnhooktools/config/"+hookType.getHookName()+"-rules.xml")) {
			// Create a document builder
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			// Parse stream as document
			Document document = documentBuilder.parse(inputStream);
			// Load each rule of rule set
			NodeList ruleNodeList = document.getElementsByTagName("rule");
			for (int i = 0; i<ruleNodeList.getLength(); i++) {
				try {
					// Load and add rule
					Rule rule = RuleSet.loadRule(ruleNodeList.item(i));
					ruleSet.rules.add(rule);
				} catch (Exception exception) {
					HookTools.LOGGER.log(Level.WARNING, "Unable to load a rule.", exception);
				}
			}
		} catch (FileNotFoundException exception) {
			// Log and exit
			HookTools.LOGGER.log(Level.SEVERE, "Unable to find hook \""+hookType.getHookName()+"\" configuration file.", exception);
			System.exit(-1);
		} catch (IOException exception) {
			// Log and exit
			HookTools.LOGGER.log(Level.SEVERE, "Unable to read hook \""+hookType.getHookName()+"\" configuration file.", exception);
			System.exit(-1);
		} catch (SAXException|ParserConfigurationException exception) {
			// Log and exit
			HookTools.LOGGER.log(Level.SEVERE, "Unable to parse hook \""+hookType.getHookName()+"\" configuration file.", exception);
			System.exit(-1);
		}
		// Return created rule set
		return ruleSet;
	}

	/**
	 * Load rule from rule configuration node.
	 * 
	 * @param ruleNode
	 *            The rule configuration node to load.
	 * @return The loaded rule.
	 * @throws Exception
	 *             Throws exception if the rule could not be loaded.
	 */
	private static Rule loadRule(Node ruleNode) throws Exception {
		// Get rule name
		NamedNodeMap namedNodeMap = ruleNode.getAttributes();
		Node nameAttributeNode = namedNodeMap.getNamedItem("name");
		if (nameAttributeNode==null)
			throw new Exception("The rule is missing name.");
		String ruleName = nameAttributeNode.getNodeValue();
		// Create rule
		Rule rule = new Rule(ruleName);
		// Load rule condition
		NodeList childNodeList = ruleNode.getChildNodes();
		for (int i = 0; i<childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			switch (childNode.getNodeName()) {
				case RuleSet.CONDITION_NODE_NAME:
					AbstractCondition condition = RuleSet.loadCondition(childNode);
					rule.setCondition(condition);
					break;
				case RuleSet.ACTION_NODE_NAME:
					AbstractAction action = RuleSet.loadAction(childNode);
					rule.addAction(action);
					break;
			}
		}
		// Return loaded rule
		return rule;
	}

	/**
	 * Load condition from condition configuration node.
	 * 
	 * @param conditionNode
	 *            The condition configuration node to load.
	 * @return The loaded condition.
	 * @throws Exception
	 *             Throws exception if the condition could not be loaded.
	 */
	private static AbstractCondition loadCondition(Node conditionNode) throws Exception {
		// Get the condition type
		NamedNodeMap namedNodeMap = conditionNode.getAttributes();
		Node typeAttributeNode = namedNodeMap.getNamedItem("type");
		if (typeAttributeNode==null)
			throw new Exception("A condition is missing type attribute.");
		String conditionType = typeAttributeNode.getNodeValue();
		try {
			// Create condition instance
			Class<? extends AbstractCondition> conditionClass = RuleSet.conditionBindings.get(conditionType);
			if (conditionClass==null)
				throw new Exception("The condition named \""+conditionType+"\" is not bound.");
			AbstractCondition condition = conditionClass.newInstance();
			// Apply condition parameters
			RuleSet.applyParameters(conditionNode, condition);
			// Check resource condition
			if (ResourceCondition.class.isInstance(condition)) {
				// Get resource condition
				ResourceCondition resourceCondition = (ResourceCondition) condition;
				// Check each filter child node
				NodeList childNodes = conditionNode.getChildNodes();
				for (int i = 0; i<childNodes.getLength(); i++) {
					// Check filter child node
					Node childNode = childNodes.item(i);
					if (!childNode.getNodeName().equals(RuleSet.FILTER_NODE_NAME))
						continue;
					try {
						// Load and add resource filter to resource condition
						AbstractResourceFilter resourceFilter = RuleSet.loadResourceFilter(childNode);
						resourceCondition.addResourceFilter(resourceFilter);
					} catch (Exception exception) {
						throw new Exception("Unable to load resource filter.", exception);
					}
				}
			}
			// Return loaded condition
			return condition;
		} catch (InstantiationException exception) {
			throw new Exception("Unable to instantiate condition \""+conditionType+"\".", exception);
		} catch (IllegalAccessException exception) {
			throw new Exception("Unable to instantiate condition \""+conditionType+"\".", exception);
		}
		// TODO operator
	}

	/**
	 * Load resource filter from filter configuration node.
	 * 
	 * @param filterNode
	 *            The filter configuration node to load.
	 * @return The loaded filter.
	 * @throws Exception
	 *             Throws exception if the filter could not be loaded.
	 */
	private static AbstractResourceFilter loadResourceFilter(Node filterNode) throws Exception {
		// Get the filter type
		NamedNodeMap namedNodeMap = filterNode.getAttributes();
		Node typeAttribudeNode = namedNodeMap.getNamedItem("type");
		if (typeAttribudeNode==null)
			throw new Exception("A filter is missing type attribute");
		String filterType = typeAttribudeNode.getNodeValue();
		try {
			// Create filter instance
			Class<? extends AbstractResourceFilter> filterClass = RuleSet.resourceFilterBindings.get(filterType);
			if (filterClass==null)
				throw new Exception("The filter named \""+filterType+"\" is not bound.");
			AbstractResourceFilter filter = filterClass.newInstance();
			// Apply filter parameters
			RuleSet.applyParameters(filterNode, filter);
			// Return loaded filter
			return filter;
		} catch (InstantiationException exception) {
			throw new Exception("Unable to instantiate filter \""+filterType+"\".", exception);
		} catch (IllegalAccessException exception) {
			throw new Exception("Unable to instantiate filter \""+filterType+"\".", exception);
		}
	}

	/**
	 * Load action from action configuration node.
	 * 
	 * @param actionNode
	 *            The action configuration node to load.
	 * @return The loaded action.
	 * @throws Exception
	 *             Throws exception if the action could not be loaded.
	 */
	private static AbstractAction loadAction(Node actionNode) throws Exception {
		// Get the action type
		NamedNodeMap namedNodeMap = actionNode.getAttributes();
		Node typeAttributNode = namedNodeMap.getNamedItem("type");
		if (typeAttributNode==null)
			throw new Exception("An action is missing type attribute.");
		String actionType = typeAttributNode.getNodeValue();
		try {
			// Create condition instance
			Class<? extends AbstractAction> actionClass = RuleSet.actionBindings.get(actionType);
			if (actionClass==null)
				throw new Exception("The action named \""+actionType+"\" is not bound.\"");
			AbstractAction action = actionClass.newInstance();
			// Apply condition parameters
			RuleSet.applyParameters(actionNode, action);
			// Return loaded action
			return action;
		} catch (InstantiationException exception) {
			throw new Exception("Unable to instantiate action \""+actionType+"\".", exception);
		} catch (IllegalAccessException exception) {
			throw new Exception("Unable to instantiate action \""+actionType+"\".", exception);
		}
	}

	/**
	 * Apply parameter to loaded object.
	 * 
	 * @param node
	 *            The loaded object node.
	 * @param loaded
	 *            The loaded object.
	 * @throws Exception
	 *             Throws exception if parameters could not be applied.
	 */
	private static void applyParameters(Node node, Object loaded) throws Exception {
		// Parse node parameters
		Map<String, String> parameters = new HashMap<>();
		// Process each child node
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i<childNodes.getLength(); i++) {
			// Get next child node
			Node childNode = childNodes.item(i);
			// Check node name
			if (!childNode.getNodeName().equals(RuleSet.PARAMETER_NODE_NAME))
				continue;
			// Get parameter node attributes
			NamedNodeMap namedNodeMap = childNode.getAttributes();
			// Get name attribute node
			Node nameAttributeNode = namedNodeMap.getNamedItem("name");
			if (nameAttributeNode==null) {
				HookTools.LOGGER.warning("A parameter is badly defined.");
				continue;
			}
			// Get node text content
			String value = childNode.getTextContent();
			if (value==null||value.trim().isEmpty()) {
				HookTools.LOGGER.warning("The parameter \""+nameAttributeNode.getNodeValue()+"\" is badly defined.");
				continue;
			}
			// Add parsed parameter
			parameters.put(nameAttributeNode.getNodeValue(), value);
		}

		// Get loaded class
		Class<?> loadedClass = loaded.getClass();
		// Check each field of loaded class
		for (Field field : loadedClass.getFields()) {
			// Declare parameter status and annotation
			boolean isParameter = false;
			ConfigurationParameter configurationParameter = null;
			// Check each field annotation
			for (Annotation annotation : field.getAnnotations()) {
				// Check annotation type
				if (!ConfigurationParameter.class.isAssignableFrom(annotation.annotationType()))
					continue;
				// Mark field as parameter
				isParameter = true;
				// Save parameter annotation
				configurationParameter = (ConfigurationParameter) annotation;
				// Skip other annotations
				break;
			}
			// Skip field if not parameter
			if (!isParameter)
				continue;
			// Get parameter name and value
			String parameterName = field.getName();
			String parameterValue = parameters.get(parameterName);
			// Check parameter value
			if (parameterValue==null) {
				// Check parameter definition
				if (configurationParameter.isRequired())
					throw new Exception("The parameter \""+parameterName+"\" is not set.");
				// Skip parameter
				continue;
			}
			// Apply parameter value
			try {
				Class<?> parameterType = field.getType();
				if (boolean.class.isAssignableFrom(parameterType)) {
					field.setBoolean(loaded, Boolean.parseBoolean(parameterValue));
				} else if (Boolean.class.isAssignableFrom(parameterType)) {
					field.set(loaded, Boolean.parseBoolean(parameterValue));
				} else if (int.class.isAssignableFrom(parameterType)) {
					field.setInt(loaded, Integer.parseInt(parameterValue));
				} else if (Integer.class.isAssignableFrom(parameterType)) {
					field.set(loaded, Integer.parseInt(parameterValue));
				} else if (String.class.isAssignableFrom(parameterType)) {
					field.set(loaded, parameterValue);
				} else if (parameterType.isEnum()) {
					field.set(loaded, Enum.valueOf((Class<Enum>) parameterType, parameterValue));
				} else {
					HookTools.LOGGER.warning("The parameter type \""+parameterType.getName()+"\" is not suppported.");
				}
				// TODO Support other types
			} catch (IllegalArgumentException exception) {
				throw new Exception("Unable to set parameter \""+parameterName+"\".", exception);
			} catch (IllegalAccessException exception) {
				throw new Exception("Unable to set parameter \""+parameterName+"\".", exception);
			}
		}
	}

	/**
	 * Private constructor.
	 */
	private RuleSet() {
		// Create rule collection
		this.rules = new ArrayList<>();
	}

	/**
	 * Process the rule.
	 * 
	 * @param hook
	 *            The hook processing the rule.
	 */
	public void process(AbstractHook hook) {
		for (Rule rule : this.rules) {
			if (rule.process(hook))
				break;
		}
	}
}