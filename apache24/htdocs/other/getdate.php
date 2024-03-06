<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];


	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select distinct(date) as date from daily_data where user_idx = $user_idx");
$rowCnt= mysqli_num_rows($result);
$arr= array();
$result2=mysqli_query($con,"select distinct(date) as date from daily_eat where user_idx = $user_idx");
$rowCnt2= mysqli_num_rows($result2);
for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
      $arr[$i]=array("date"=>$row['date'],"type" => "1");
    
}
$index = $rowCnt;
for($j=$rowCnt;$j<$rowCnt+$rowCnt2;$j++){
  $row2= mysqli_fetch_array($result2, MYSQLI_ASSOC);
  for($k=0;$k<$rowCnt;$k++){
  if(strcmp($row2['date'],$arr[$k]['date'])==0){
    $arr[$k]=array("date"=>$row2['date'],"type"=>"3");   
    break;
  }
  }
  if($k==$rowCnt){
  $arr[$index]=array("date"=>$row2['date'],"type" => "2");
      $index++;
  }
    
  }
  



  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>