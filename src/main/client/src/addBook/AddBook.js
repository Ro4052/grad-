import React, { Component } from 'react'
import * as axios from "axios";

export default class AddBook extends Component {
    
    onChange(e) {
       let input = e.target.value.trimStart();
       e.target.value = input;
       if (e.target.id==="publishDate" && e.target.value.length > 0){e.target.value = e.target.value.slice(0, 4);}
    }

    checkDate(e) {
        
    }

    render() {
        let isbn;
        let title;
        let author;
        let publishDate;
        let date = new Date().getFullYear();

        return (
            <div>
                <form  onSubmit={async function(e) {
                    e.preventDefault();
                    let newBook = {
                        "isbn": isbn.value.trim(),
                        "title": title.value.trim(),
                        "author": author.value.trim(),
                        "publishDate": publishDate.value.trim()
                    }
                    await axios.post('/api/books', newBook);
                    isbn.value = "";
                    title.value = "";
                    author.value = "";
                    publishDate.value = "";


                }}>
                    <div>
                        <label>ISBN</label>
                        <input ref={node => isbn = node} type="text" pattern="^[0-9]{10}|[0-9]{13}$" maxLength="13"
                        placeholder="ISBN" id="isbn" onChange={this.onChange} ></input>
                    </div>
                    <div>
                        <label>Title</label>
                        <input ref={node => title = node} type ="text" placeholder="Title"
                        id="title" maxLength="200" required onChange={this.onChange}></input>
                    </div>
                    <div>
                        <label>Author</label>
                        <input ref={node => author = node} type="text" placeholder="Author"
                        id="author" maxLength="200" required onChange={this.onChange}></input>
                    </div>
                    <div>
                        <label>Publish Date</label>
                        <input ref={node => publishDate = node} type ="number" placeholder="Publish Date"
                        id="publishDate" pattern="^[0-9]{4}$" maxLength="4" max={date} onChange={this.onChange}></input>
                    </div>
                    <div>
                        <button type="submit">Add</button>
                    </div>
                </form>
            </div>
        )
    }
}