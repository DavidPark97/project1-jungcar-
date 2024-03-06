<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user =  $_POST['user_idx'];

$query1 = "SELECT * FROM connect WHERE user_idx =".$user.";";
$result = mysqli_fetch_array(mysqli_query($con, $query1));

$trainer = $result["trainer_idx"]; 



$query2 = "SELECT * FROM trainer where trainer_idx=$trainer;";
$result2 = mysqli_query($con, $query2);

$rowCnt = mysqli_num_rows($result2);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
    $row= mysqli_fetch_array($result2, MYSQLI_ASSOC);
    $arr[$i]= $row;      
}

$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo "$jsonData";
mysqli_close($con);
?>