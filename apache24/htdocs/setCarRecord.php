<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$year = $_POST['year'];
$month = $_POST['month'];




header('content-type: text/html; charset=utf-8');



$query = "select ifnull(round(sum((select sum(distance) as distance from drive_history where year(date)=$year and car_idx=$car_idx)/(select sum(charge/unit) from oil_history where year(date)=$year and car_idx=$car_idx)),2),0) as effi,
 ifnull((select sum(distance) from drive_history where year(date)=$year and car_idx=$car_idx),0) as distance,
  ifnull((select sum(charge/unit) from oil_history where year(date)=$year and car_idx =$car_idx),0) as amount,
 ifnull((select sum(charge) from oil_history where year(date)=$year and car_idx=$car_idx),0) as charge;";

$query2 = "select ifnull(round(sum((select sum(distance) as distance from drive_history where year(date)=$year and car_idx=$car_idx and month(date)=$month)/(select sum(charge/unit) 
from oil_history where year(date)=$year and car_idx=$car_idx and month(date)=$month)),2),0) as effi, ifnull((select sum(distance) from drive_history where year(date)=$year 
and car_idx=$car_idx and month(date)=$month),0) as distance, ifnull((select sum(charge/unit) from oil_history where year(date)=$year and car_idx =$car_idx and month(date)=$month),0) as amount, 
ifnull((select sum(charge) from oil_history where year(date)=$year and car_idx=$car_idx and month(date)=$month),0) as charge;";


$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


$result2=mysqli_query($con, $query2);
$rowCnt2= mysqli_num_rows($result2);
$arr2= array();

for($i=0;$i<$rowCnt2;$i++){
      $row= mysqli_fetch_array($result2, MYSQLI_ASSOC);
        $arr2[$i]= $row;      
}




  $jsonData=json_encode(array("year"=>$arr,"month"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>