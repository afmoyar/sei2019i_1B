<?php

include 'conection.php';
$id=$_POST['id'];
$name=$_POST['name'];
$password=$_POST['password'];

$sql="INSERT INTO user VALUES('$id','$name','$password')";
mysqli_query($conection,$sql) or die (mysqli_error());
mysqli_close($conection);

?>