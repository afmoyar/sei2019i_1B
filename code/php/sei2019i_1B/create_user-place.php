<?php
include 'connection.php';

$id=$_POST['id'];
$latitude=$_POST['latitude'];
$longitude=$_POST['longitude'];
$sql="INSERT INTO user_place VALUES ('$id', 'latitude', 'longitude')";
$result = mysqli_query($connection,$sql) or die (mysqli_error());

if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
