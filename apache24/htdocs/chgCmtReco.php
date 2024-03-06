<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user_idx = $_POST['user_idx'];
$comment_idx = $_POST['comment_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select * from cmt_reco where user_idx = $user_idx and comment_idx =$comment_idx";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$flag = 0;
if($rowCnt!=0){
    $query2 = "delete from cmt_reco where comment_idx = $comment_idx and user_idx = $user_idx";
    
}else{
    $query2 = "insert into cmt_reco (user_idx,comment_idx) values ($user_idx,$comment_idx)";
    $flag = 1;
}
$result2=mysqli_query($con, $query2);

$query3 = "select count(*) as reco from cmt_reco where comment_idx =$comment_idx";

$result3=mysqli_query($con, $query3);
$rowCnt2= mysqli_num_rows($result3);
$arr= array();

for($i=0;$i<$rowCnt2;$i++){
      $row= mysqli_fetch_array($result3, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}
$arr[$i]=array("flag"=>$flag);


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";


mysqli_close($con);
?>