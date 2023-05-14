import axios from "axios";
import { useState } from "react";
import './CommentForm.css'
import Comment from './Comment'

export default function CommentForm(props) {

  const { commodity } = props;

  const isLoggedIn = localStorage.getItem('userLoggedIn');

  const [commentText, setCommentText] = useState('');
  const handleSubmit = async e => {
    e.preventDefault();
    // try {
    //   const data = { commentMovieId: movieId, text: commentText };
    //   const response = await axios.post('/comments/', data);
    //   const newComment = response.data.content;
    //   addComment(newComment);
    //   setCommentText('');
    // } catch (e) {
    //   console.log(e);
    // }
  }


  return (
    <div className='commoent-box'>
      <div class="comments">
        <div class=" d-flex align-items-center ">
          <div class="comment-text">
            Comments
          </div>
          <div class="comment-num">
            ({Object.keys(commodity.comments).length})
          </div><br /><br />
        </div>
        {commodity &&
          commodity.comments.map((comment) => (
            <Comment key={comment.id} comment={comment} />
          ))}
        <div class="container comment-box ">
          <div class="comment-box-header">
            Submit your opinion
          </div>
          <div class="d-flex align-items-center">
            <div class="comment-text-box "></div>
            <button class="submit-button  ">
              Post
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}