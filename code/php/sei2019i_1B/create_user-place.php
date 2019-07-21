<?php
include 'connection.php';

$id=$_POST['id'];
$latitude=$_POST['latitude'];
$longitude=$_POST['longitude'];
$sql="INSERT INTO user_place(user_id, place_latitude, place_longitude) VALUES ('$id', '$latitude', '$longitude')";
$result = mysqli_query($connection,$sql) or die (mysqli_error());

if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
