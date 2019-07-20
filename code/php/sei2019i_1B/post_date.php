<?php

include 'connection.php';

$id = $_GET['id'];
$date = $_GET['date'];

$query = "UPDATE administrator SET limit_date=\"$date\" WHERE id = \"$id\"";

$result = mysqli_query($connection, $query) or die (mysqli_error());

if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
