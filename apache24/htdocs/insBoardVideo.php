<?php
 $board_idx  = $_POST['board_idx'];
 $type = $_POST['type'];
 $videosFile = $_FILES['file']['name'];
 
 $file_path = "Video_dir/";

// 저장할 경로 + 새로운 이름을 지정한다.
 $file_name = $file_path."br".$board_idx.".mp4";



 if(isset($videosFile)){

// 임시폴더에서  위에서 작성한 경로로  새로운 이름으로  보내준다.
  if( move_uploaded_file($_FILES['file']['tmp_name'], $file_name)){
    $con=mysqli_connect("localhost","root","1234","jungcar");

    if(mysqli_connect_error($con))
    echo "Failied to connect : " .mysqli_connect_error();

    $result = mysqli_query($con, "insert into board_content (board_idx,cont,type) values($board_idx,'$file_name',$type)");

    
  }else{

    
  }

 }else{
  echo "실패";
 }
?>