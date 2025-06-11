<?php


	$request_id = $_GET["request_id"];
	$status = $_GET["status"];
	
	
	$host="localhost";
	$uname="root";
	$pass="";
	$dbname="login-registration";
	
		
	$dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	mysqli_select_db($dbh, $dbname);
			
	$sql = "UPDATE request_details SET status = '".$status."' WHERE request_id = ".$request_id;
	
	echo $sql;
	mysqli_query($dbh, $sql);
	mysqli_close($dbh);
	
?>