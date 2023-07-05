import axios from "axios";
import { useState } from "react";
import './CommentForm.css'
import Comment from './Comment'
import Request from "../functions/Request";


export default function CommentForm(props) {

  const { commodity,setCommodity } = props;

  const isLoggedIn = localStorage.getItem('userId');

  const [commentText, setCommentText] = useState('');
  const handleSubmit = async e => {
    e.preventDefault();
    try {

      const data = { commentCommodityId: commodity.id, text: commentText };
      const response = await Request.post('/comments/', data);

      const newComment = response.data.content;
      addComment(newComment);
      setCommentText('');
    } catch (e) {
      console.log(e);
    }
  }
  const addComment = newComment => {
    commodity.comments.push(newComment);
    

  }

  const updateComment = editedComment => {
    const commentIndex = commodity.comments.findIndex(x => x.id === editedComment.id);
    commodity.comments[commentIndex] = editedComment;
    setCommodity({ ...commodity });
  }
  return (

    < div className='commoent-box'>
      <div class="comments">
        <div class=" d-flex align-items-center ">
          <div class="comment-text">
            Comments
          </div>

          {commodity.comments ?
            <div class="comment-num">
              ({Object.keys(commodity.comments).length})
            </div>
            :
            <div class="comment-num">
              0
            </div>}


          <br /><br />
        </div>
        {commodity.comments &&
          commodity.comments.map((comment) => (
            <Comment key={comment.id} comment={comment} updateComment={updateComment}/>
          ))}
        <div class="container comment-box ">
          <div class="comment-box-header">
            Submit your opinion
          </div>
          <form class="d-flex align-items-center" onSubmit={handleSubmit}>
            <input class="comment-text-box "

              id="comment-input"
              type="text"
              placeholder="Submit here"
              aria-label="Submit here"
              value={commentText}
              required
              onChange={e => { setCommentText(e.target.value) }}
            ></input>
          <button class="submit-button" type="submit"
          >
            Post
          </button>
        </form>
      </div>
    </div>
    </div >

  )
}