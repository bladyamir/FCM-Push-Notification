<?php
require "config.php";
$token=$_POST['token'];
$name=$_POST['name'];
$sql="insert into token_table (name,token) values ('{$name}','{$token}')";
if($con->query($sql)===true){
	echo "token inserted";
}else{
	echo "insertion error";
}
$con->close;
?>