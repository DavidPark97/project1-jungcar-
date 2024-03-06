<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$sell_idx = $_POST['sell_idx'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select option_name, option_type_name 
from optionn, sell_option,option_type where optionn.option_idx = sell_option.option_idx and optionn.option_type_idx = option_type.option_type_idx and sell_idx=$sell_idx
order by option_type.option_type_idx, optionn.option_idx;");

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