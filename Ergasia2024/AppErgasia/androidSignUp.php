<?php
	
	$data = array();
	
	$username = $_GET["username"];
	$name = $_GET["name"];
	$password = $_GET["password"];

	$host="localhost";
	$uname="root";
	$pass="";
	$dbname="login-registration";
	
	$dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	mysqli_select_db($dbh, $dbname);


	//username name matpoints totalpoints password role
	
	$sql = "INSERT into user_details values('" . $username	. "','" . $name . "', '0,0,0,0', '0', '" . $password . "', '0')";
	
	echo $sql;
	mysqli_query($dbh, $sql);
	mysqli_close($dbh);
?>