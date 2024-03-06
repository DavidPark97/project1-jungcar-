<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user =  $_POST['user_idx'];
$category =  $_POST['category'];

$query1 = "SELECT preference, inbody, gender FROM user WHERE user_idx =".$user.";";
$result = mysqli_fetch_array(mysqli_query($con, $query1));

$prefer = $result["preference"];
$inbody = $result["inbody"];
if($inbody>=100){
    $inbody=100;
}else{
$a = 5;
$inbody = intval($inbody/$a);
$inbody = intval($inbody*5);
}

$in = (int)$inbody;
$gender = $result["gender"];

$query2 = "SELECT w.workout_idx, w.workout_name, w.img, w.part_img, p.percentage FROM popular as p, workout as w WHERE p.workout_idx = w.workout_idx and p.category = ".$category." and p.preference =".$prefer." and p.inbody =".$inbody." and p.gender =".$gender.";";
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