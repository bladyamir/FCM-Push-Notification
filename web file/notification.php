<?php
require "config.php";


	

	 	function send_notification($token){
	 		 $message = $_POST["message"];
	 		$title = $_POST["title"];

	 		$url = 'https://fcm.googleapis.com/fcm/send' ;
			$key = "AIzaSyBoO1Piw4y_7LuuoiiZZULMUEuAfsQhslo";

			
			$header = array(
	 		'Authorization: key=' .$key,
	 		'Content-Type: application/json'
	 		);

	 	$fields=array('to' => $token,
	 			'notification' => array('title' => $title,
	 				'body' => $message
	 				)
	 		);

	 	

	 	$payload = json_encode($fields);

	 	

	

	 		$curl_session = curl_init();
	 		curl_setopt($curl_session, CURLOPT_URL, $url);
	 		curl_setopt($curl_session, CURLOPT_POST, true);
	 		curl_setopt($curl_session, CURLOPT_HTTPHEADER, $header);
	 		curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
	 		curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
	 		curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);

	 		$res=curl_exec($curl_session);

			
	 		if($res === false){
	 			die("error: ".curl_error($curl_session));
	 		}
	 curl_close($curl_session);
	 
	 

	}



	


	$sql = $_POST["sql"];

	$result = $con->query($sql);
	while ($row = $result->fetch_assoc()) {
		$token=$row["token"];
		send_notification($token);
		
	}
	echo  "notification  sent<br>";
	echo "<a href='index.html'>Go Home</a>";

?>