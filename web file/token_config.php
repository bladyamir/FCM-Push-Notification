<?php
	$serverName="mysql4.000webhost.com";
	$userName="a8167535_amir";
	$password="a123456";
	$dbName="a8167535_tok";
	$con=new mysqli($serverName,$userName,$password,$dbName);

	if($con->connect_error){
		die("connection faild {$con->connect_error}");
	}else{
		echo "connected";
	}
?>