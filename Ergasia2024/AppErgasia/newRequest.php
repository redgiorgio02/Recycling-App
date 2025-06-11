<?php
	
//request_details
//username, status, material_type, material_points, material_weight
	$data = array();
	$username = $_GET["username"];
	$material_type = $_GET["material_type"];
	$material_points = $_GET["material_points"];
	$material_weight = $_GET["material_weight"];
	//$username = $_GET["username"];
	//$password = $_GET["password"];

	$host="localhost";
	$uname="root";
	$pass="";
	$dbname="login-registration";
	//$dbname="recycle";
	
	$dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	mysqli_select_db($dbh, $dbname);
			
	$sql = "INSERT into request_details(username, status, material_type, material_points, material_weight) values('" . $username	. "','PENDING','" . $material_type . "','".$material_points."','".$material_weight."')";
	
	//$sql = "INSERT into data values('" . $username . "','" . $password . "','')";
	echo $sql;
	mysqli_query($dbh, $sql);
	mysqli_close($dbh);
?>