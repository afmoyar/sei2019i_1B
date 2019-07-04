<?php

include 'connection.php';
$id = $_POST['id'];
$password = $_POST['password'];

$sql = "SELECT * FROM user WHERE id = '".$id."' AND password = '".$password."'";

$result = mysqli_query($connection, $sql);

if($data = mysqli_fetch_array($result)){
	echo '1';
	}
?>