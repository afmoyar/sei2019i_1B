<?php
include 'connection.php';

$limit_date=$_POST['limit_date'];

$consulta = "UPDATE administrator SET limit_date = '$limit_date' WHERE id = 'Otro'";
mysqli_query($connection,$consulta)or die (mysqli_error());
mysqli_close($connection);

?>
