# SVN Hook Tools

## Presentation
The SVN Hook Tools is a rule engine designed to quickly and easily add task on Subversion repository hooks.
For each hook is associated a rule-set where each rule contains a condition and a list of actions to perform. For example, you may block a commit without a comment, allow log message edition from some users only, or even execute programs or Web requests after creating a tag.

## Installation

### Prerequisites
The SVN Hook Tools requires a 1.7 JVM.

### Deployement
To deploy the tool, you will need to copy the `svn-hook-tools.jar` and its `config` directory at the same location, for example the `hook` directory of the repository.

### Call the tool
To call the tool, edit the respository hook shell scripts you want to create tasks and add a the following call to the SVN Hook Tools for Linux:
```bash
java -jar svn-hook-tools.jar $(basename $0) $@
```
or for Windows:
```bash
java -jar svn-hook-tools.jar %~n0 %*
```
The arguments needed to call the tool are the hook name and the hook script arguments.

## Setup

### Logging setup
The SVN Hook Tools uses a  `java.util.logging` logger. You may configure the logging feature using the configuration file available in `config/logging.properties`. The log will be really useful during the rule definition process to retrieve configuration errors. By default, the tool writes a human readable `svn-hook-tools.log` file in the user home directory.

### Condition and action binding
The tool comes with built-in conditions and actions. Nevertheless, you may also develop and add you own condition or action and use it throw dynamic class loading. The configuration file responsive for loading and binding classes could be found in `config/bindings.properties`.

### Built-in conditions
The tool comes with some built-in conditions. Here is a summary table of conditions.

| Name | Description | Parameters |
| ---- | ----------- | ---------- |
| _all_ | An operator condition valid if all nested conditions are valid. | |
| _any_ | An operator condition valid if at least one of nested condition is valid. | |
| _author_ | A condition that test the Subversion user name. | _name_: mandatory, the name to check,<br> _nameComparison_: _IS_ by default, the name comparison method. |
| _emptyCommitLog_ | A condition valid if the commit log message is empty. | |
| _minLengthCommitLog_ | A condition valid if the commit log message length is greater than the requested length. | _length_: mandatory, the minimum length of the commit log message. |
| _patternCommitLog_ | A condition valid if the commit log message valid the given pattern. | _pattern_: mandatory, the pattern of the commit log message. |
| _resource_ | A condition valid if any resource operation valid all related resource filters. | See resource filters description below. |
| _not_ | An operator condition valid if the nested condition is not valid. | |

The resource condition must include resource filters to be validated. Here is a summary table of resource filters.

| Name | Description | Parameters |
|------|-------------|------------|
| _FileExtension_ | A resource filter based on file extension. | _fileExtension_: mandatory, the file extension to check,<br> _fileExtensionComparison_: _IS_ by default, the file extension comparison method. |
| _FileName_ | A resource filter based on file name. | _fileName_: mandatory, the file name to check. |
| _FileLocation_ | A resource filter based on resource location. | _type_: no check by default, the location type to check (_ROOT_LOCATION_, _TRUNK_LOCATION_, _BRANCHES_LOCATION_, TAGS_LOCATION_, A_BRANCH_LOCATION_, _A_TAG_LOCATION_, _IN_TRUNK_LOCATION_, _IN_A_BRANCH_LOCATION_, _IN_A_TAG_LOCATION_, _IN_ROOT_LOCATION_),<br> _projectName_: no check by default, the project name to check,<br> _projectNameComparison_: _IS_ by default, the project name comparison method,<br> _branchName_: no check by default, the branch name to check,<br> _branchNameComparison_: _IS_ by default, the branch name comparison method,<br> _tagName_: no check by default, the tag name to check,<br> _tagNameComparison_: _IS_ by default, the tag name comparison method,<br> _path_: no check by default, the path to check,<br> _pathComparison_: _CONTAINS_ by default, the path comparison method. |
| _Operation_ | A resource filter based on the operation done on the resource. | _operation_: mandatory, the operation to check (_ADDED_, _COPIED_, _DELETED_, _UPDATED_, _PROPERTY_CHANGED_, _LOCK_, _UNLOCK_). |
| PropertyChange | A resource filter based on resource property change. | _name_: mandatory, the name of the property to check,<br> _oldValue_: no check by default, the old value of the property to check,<br> _oldValueComparison_: _IS_ by default, the old value of the property comparison method,<br> _newValue_: no check by default, the new value of the property to check,<br> _newValueComparison_: _IS_ by default, the new value of the property comparison method. |
| FileType | A resource filter based on resource type. | _type_: mandatory, the type of the resource to check (_DIRECTORY_ or _FILE_). |


### Built-in actions
The tool comes with some built-in actions. Here is a summary table of actions.

| Name | Description | Parameters |
| ---- | ----------- | ---------- |
| _error_ | An action that raise an error to the Subversion client. | _code_: mandatory, the error code to return,<br> _message_: the error message to send. |
| _exec_ | An action that execute a program. | _command_: mandatory, the program command to execute,<br> _parameters_: empty by default, the program parameters to pass,<br >_waitFor_: _FALSE_ by default, the flag to define if the engine should wait for the terminaison of the executed program. |
| _log_ | An action that write a log entry. | _message_: mandatory, the log message,<br> _level_: _INFO_ by default, the log level (_FINEST_, _FINER_, _FINE_, _CONFIG_, _INFO_, WARNING_ or _SEVERE_). |
| _request_ | An action that make a HTTP request. | _url_: mandatory, the URL to request,<br> _type_: _GET_ by default, the request type (_POST_ or _GET_),<br> _headers_: the request headers,<br >_data_: the request data to send. |


### Rules declaration
A rule-set for a hook is described in a XML file. The name of the file  must be the same of the hook script suffixed by `-rules.xml` and be located in the `config` directory, `config/pre-commit-rules.xml` for example.
The root node should be a `ruleset` node containing one `rule` node for each task you want to make. A `rule` node must have a `name` attribute to describe it.

```xml
<rule-set>
	<rule name="Empty commit log">
	</rule>
	<rule name="Block in trunk modification">
	</rule>
</rule-set>
```

The `rule` node may contains a `condition` node. If the `condition` node is missing, the rule actions will always be triggered with the hook. The `condition` node must have a `type` attribute to define the kind of condition. All available condition types are defined in the `config/bindings.properties` configuration file.
```xml
<rule-set>
	<rule name="Empty commit log">
		<condition type="emptyCommitLog" />
	</rule>
	<rule name="Block in trunk modification">
		<condition type="resource">
			<filter type="location">
				<parameter name="type">IN_TRUNK_LOCATION</parameter>
			</filter>
		</condition>
	</rule>
</rule-set>
```

If you want to add more than one condition, you may imbricate `condition` nodes in operator typed condition (`all`, `any`, `not`). For example, if you want to block in trunk modification for all user except admin, you will have the following condition imbrication.
```xml
<rule-set>
	<rule name="Empty commit log">
		<condition type="emptyCommitLog" />
	</rule>
	<rule name="Block non admin in trunk modification">
		<condition type="all">
			<condition type="not">
				<condition type="author">
					<parameter name="name" value="admin" />
				</condition>
			</condition>
			<condition type="resource">
				<filter type="location">
					<parameter name="type">IN_TRUNK_LOCATION</parameter>
				</filter>
			</condition>
		</condition>
	</rule>
</rule-set>
```

After defining your condition, add any `action` node for each task you want to perform if the condition is met. The `action` node must have a `type` parameter. All available action types are defined in the `config/bindings.properties` configuration file.
```xml
<rule-set>
	<rule name="Empty commit log">
		<condition type="emptyCommitLog" />
		<action type="error">
			<parameter name="code">-10</parameter>
			<parameter name="message">The commit message could not be empty.</parameter>
		</action>
	</rule>
	<rule name="Block in trunk modification">
		<condition type="resource">
			<filter type="location">
				<parameter name="type">IN_TRUNK_LOCATION</parameter>
			</filter>
		</condition>
		<action type="error">
			<parameter name="code">-11</parameter>
			<parameter name="message">Forbidden to touch trunk.</parameter>
		</action>
	</rule>
</rule-set>
```

Doing so, you will define your entire hook rule-set. Each rule performs independently in the sequential order.

## Extending the tool

The SVN Hook Tools may be extended by developping conditions and actions. Create a Java project with svn-hook-tools.jar dependency and extend the following classes:

| Class | Purpose |
|-------|---------|
| _action.AbstractAction_ | Create a new action. |
| _condition.AbstractCondition_ | Create a new condition. |
| _condition.operator.AbstractGroupCondition_ | Create a new operator condition. |
| _condition.resource.filter.AbstractResourceFilter_ | Create a new resource filter (for resource condition). |

Each child of those classes may have parameters. Add them public member annoted with `@ConfigurationParameter` to dynamically load and set parameter value on rule-set parsing. The class field name must match the `name` attribute value of the related `parameter` node. Once the child classes done, export you project as jar and add it to the callpath when you call the tool. To be able to use your new conditions and actions, you must declare theirs types in the `config/bindings.properties` (a mapping between type names and fully qualified class names).