<?php
	
	
	 $data = array();
	 
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="login-registration";
	 
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 
	 $sql = "SELECT * FROM request_details WHERE status = 'PENDING'";
	 
	 $result = mysqli_query($dbh, $sql);
	 while ($row = mysqli_fetch_array($result)) {
		$nested_data = array();
		$nested_data['request_id'] = $row['request_id'];
		$nested_data['username']= $row['username'];
		$nested_data['material_type']= $row['material_type'];
		$nested_data['material_weight']= $row['material_weight'];
		$nested_data['material_points']= $row['material_points'];
		$data[$row['request_id']] = $nested_data;
	 }
	 
	 header("Content-Type: application/json");
	 echo json_encode($data);
	 mysqli_close($dbh);
?>
