<?php

	 $data = array();
	 
	 $username = $_GET['username'];
	 
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="login-registration";
	 //$dbname="recycle";
	 
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 	 
	 $sql = "SELECT * FROM user_details WHERE username = '".$username."'";
	 //$sql = "SELECT * FROM data WHERE username = '$username'";
	 
	 $result = mysqli_query($dbh, $sql);
	 if(mysqli_num_rows($result) === 1){
		$row = mysqli_fetch_array($result);
		$nested_data = array();
		$nested_data['name'] = $row['name'];
		$nested_data['material_points'] = $row['material_points'];
		$nested_data['total_points'] = $row['total_points'];
		$nested_data['role']= $row['role'];
		$nested_data['password']= $row['password'];
		$data[$row['username']] = $nested_data;
	 }/*
	 else{
		$data["none"] = "none";
	 }
	 */
	 
	 header("Content-Type: application/json");
	 echo json_encode($data);
	 mysqli_close($dbh);
// db name login-registration
//table 1
//user_details
//username, name, material_points, total_points, password, role
//table 2
//request_details
//request_id, username, status, material_type, material_points, material_weight
?>