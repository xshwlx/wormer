var TaskName = {
	TASK_CREATE_VM : "vmreate",

	TASK_INSTALL_WAS : "wasinstall",

	TASK_INSTALL_WEBSERVER : "webserverinstall",

	TASK_INSTALL_NC : "ncinstall",

	TASK_INSTALL_UAP : "uapinstall",

	TASK_INSTALL_WLS : "wlsinstall",

	TASK_UNINSTALL_WINSW : "uninstallSoftware",
	
	TASK_INSTALL_GENERAL : "generalinstall",
};

var TaskType = {
		REMOTE_LONG_TASK : "REMOTE_LONG_TASK",//远程任务

		LOCAL_LONG_TASK : "LOCAL_LONG_TASK",//本地任务
		
		INSTALL_TASK : "INSTALL_TASK",//安装任务
		
		INSTALL_PROGRESS_TASK : "INSTALL_P_TASK",//带进度的安装任务

		MULTI_TASK : "MULTI_TASK",//多重任务

		MULTI_INSTALL_TASK : "MULTI_INSTALL_TASK",//多重安装任务
		
		APPLICATION_TASK : "APPLICATION_TASK",//应用相关任务
};

var TaskStatus = {
	/**
	 * 连接失败
	 */
	DISCONNECT : "Disconnect",
	/**
	 * 任务开始
	 */
	START : "Start",
	/**
	 * 任务正在执行
	 */
	EXECUTING : "Executing",
	/**
	 * 任务结束
	 */
	FINISHED : "Finished",
	/**
	 * 任务失败
	 */
	FAILED : "Failed",
	/**
	 * 任务成功
	 */
	SUCCESS : "Success",
	/**
	 * 任务未知
	 */
	UNKNOWN : "Unknown",
};
