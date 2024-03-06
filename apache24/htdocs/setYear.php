<?php
$con=mysqli_connect("localhost","root","1234","jungcar");




$car_idx = $_POST['car_idx'];



header('content-type: text/html; charset=utf-8');



$query = "select distinct(year(date)) as year from drive_history where car_idx = $car_idx union 
select distinct(year(date)) as year from oil_history where car_idx = $car_idx 
union select distinct(year(date)) as year from maintain_history where car_idx = $car_idx order by year asc";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

for($i=0;$i<$rowCnt;$i++){
    $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
      $arr[$i]= $row;      
}


$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo "$jsonData";

mysqli_close($con);
?>