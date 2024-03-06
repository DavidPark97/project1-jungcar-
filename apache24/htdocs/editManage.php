<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$main_idx = $_POST['main_idx'];





header('content-type: text/html; charset=utf-8');



$query = "select type_name,img,maintain.main_type,charge,main_idx from maintain, maintain_history
 where maintain.main_type = maintain_history.main_type and car_idx = (select car_idx from maintain_history where main_idx = $main_idx) and
 date(date) = (select date(date) from maintain_history where main_idx = $main_idx) order by maintain.main_type;";


	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>