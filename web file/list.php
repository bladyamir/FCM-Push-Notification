<?php
require "config.php";

$sql="select id,name from token_table";
$result = $con->query($sql);

$data = array();
while ($row = $result->fetch_assoc()) {
	array_push(
		$data, array(
	"name" => $row["name"],
	"id"   =>  $row["id"]	
		)
	);
}

$result = json_encode(array("data" => $data));


echo $result;

?>