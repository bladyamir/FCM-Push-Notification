<?php
	$serverName="mysql4.000webhost.com";
	$userName="a6712455_PN";
	$password="amir420";
	$dbName="a6712455_PN";
	$con=new mysqli($serverName,$userName,$password,$dbName);

	if($con->connect_error){
		die("connection faild {$con->connect_error}");
	}
	
?>