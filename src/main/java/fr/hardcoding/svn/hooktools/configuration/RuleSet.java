package fr.hardcoding.svn.hooktools.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.hardcoding.svn.hooktools.action.AbstractAction;
import fr.hardcoding.svn.hooktools.action.ErrorAction;
import fr.hardcoding.svn.hooktools.condition.AbstractCondition;
import fr.hardcoding.svn.hooktools.condition.commit.EmptyCommitLogCondition;
import fr.hardcoding.svn.hooktools.hook.AbstractHook;
import fr.hardcoding.svn.hooktools.hook.HookType;

/**
 * This class represents a rule set for a hook.
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
public class RuleSet {
	/** The rule collection. */
	private final List<Rule> rules;

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
				// Load and add rule
				Rule rule = RuleSet.loadRule(ruleNodeList.item(i));
				if (rule!=null)
					ruleSet.rules.add(rule);
			}
		} catch (FileNotFoundException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		} catch (SAXException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		} catch (ParserConfigurationException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		// Return created rule set
		return ruleSet;
	}

	/**
	 * Load rule from rule configuration node.
	 * 
	 * @param ruleNode
	 *            The rule configuration node to load.
	 * @return The loaded rule, <code>null</code> if the rule could not be loaded.
	 */
	private static Rule loadRule(Node ruleNode) {
		// Get rule name
		NamedNodeMap namedNodeMap = ruleNode.getAttributes();
		Node nameAttributeNode = namedNodeMap.getNamedItem("name");
		if (nameAttributeNode==null)
			return null;
		String ruleName = nameAttributeNode.getNodeValue();
		// Create rule
		Rule rule = new Rule(ruleName);
		// Load rule condition
		NodeList childNodeList = ruleNode.getChildNodes();
		for (int i = 0; i<childNodeList.getLength(); i++) {
			Node childNode = childNodeList.item(i);
			switch (childNode.getNodeName()) {
				case "condition":
					AbstractCondition condition = RuleSet.loadCondition(childNode);
					if (condition!=null)
						rule.setCondition(condition);
					break;
				case "action":
					AbstractAction action = RuleSet.loadAction(childNode);
					if (action!=null)
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
	 * @return The loaded condition, <code>null</code> if the condition could not be loaded.
	 */
	private static AbstractCondition loadCondition(Node conditionNode) {
		// Get the condition type
		NamedNodeMap namedNodeMap = conditionNode.getAttributes();
		Node typeAttributeNode = namedNodeMap.getNamedItem("type");
		if (typeAttributeNode==null)
			return null;
		String conditionType = typeAttributeNode.getNodeValue();
		// Instantiate condition
		AbstractCondition condition = null;
		// TODO Use alias definition
		switch (conditionType) {
			case "emptyCommitLog":
				condition = new EmptyCommitLogCondition();
				break;
			default:
				return null;
		}
		// TODO param
		// TODO operator
		// Return loaded condition
		return condition;
	}

	/**
	 * Load action from action configuration node.
	 * 
	 * @param actionNode
	 *            The action configuration node to load.
	 * @return The loaded action, <code>null</code> if the action could not be loaded.
	 */
	private static AbstractAction loadAction(Node actionNode) {
		// Get the action type
		NamedNodeMap namedNodeMap = actionNode.getAttributes();
		Node typeAttributNode = namedNodeMap.getNamedItem("type");
		if (typeAttributNode==null)
			return null;
		String actionType = typeAttributNode.getNodeValue();
		// Instantiate action
		AbstractAction action = null;
		// TODO Use alias definition
		switch (actionType) {
			case "error":
				action = new ErrorAction();
				break;
			default:
				return null;
		}
		// TODO param
		// Return load action
		return action;
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