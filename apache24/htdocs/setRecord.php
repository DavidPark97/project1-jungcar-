<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select ifnull(sum(charge),0) as num from oil_history where car_idx = $car_idx union all select ifnull(sum(distance),0)+ trvl_dstnc as num from car left outer join drive_history on  car.car_idx = drive_history.car_idx,refer 
where car.car_idx = $car_idx  and car.plnumber = refer.vhrno
union all select ifnull(sum(charge),0) as num from maintain_history where car_idx = $car_idx
union all select ifnull(count(*),0) as num from crash_history where car_idx = $car_idx 
union all select ifNULL(round(sum((select sum(distance) as distance from drive_history where car_idx=$car_idx)/(select sum(charge/unit) from oil_history where car_idx=$car_idx)),2),0) as num";

$query2 = "select maintain.main_type, type_name, max(maintain_history.date) as date1,IFNULL(stdkm - (select sum(distance) from drive_history where drive_history.car_idx = maintain_history.car_idx and drive_history.car_idx = $car_idx and drive_history.date >= max(maintain_history.date)),stdkm-(select sum(distance) from drive_history where car_idx = $car_idx)) as dist, 
if(stdmth=0,0,IFNULL(stdmth-timestampdiff(month,max(maintain_history.date),'2023-07-01'),stdmth-period_diff(concat(year('2023-07-01'),date_format('2023-07-01','%m')),
(select regist_de from refer,car where car.plnumber = refer.vhrno and car_idx = $car_idx))
))as diff from drive_history right outer join maintain_history on maintain_history.car_idx = drive_history.car_idx
 right outer join maintain on maintain_history.main_type = maintain.main_type and maintain_history.car_idx=$car_idx group by maintain.main_type having dist <0 or diff < 0
order by maintain.main_type;";


$result2=mysqli_query($con, $query2);
$rowCnt2= mysqli_num_rows($result2);
$rowCnt2= "$rowCnt2";
$arr2 = array("num"=>$rowCnt2);

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}
$arr[$i] = $arr2;


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>