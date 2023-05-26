import './SearchBarHeader.css'

import { useState } from "react"
import { useSearchParams } from "react-router-dom"
import magnifier from '../images/magnifier.png'




export default function SearchBarHeader() {

    const [filterBy, setFilterBy] = useState('name');
    const [searchValue, setSearchValue] = useState('');
    const [searchParams, setSearchParams] = useSearchParams();
    const handleSearchSubmit = e => {
        e.preventDefault();
        setSearchParams({
            filterBy,
            searchValue
        })
    }
    return (
        <>
            <form class="search-box" onSubmit={handleSearchSubmit}>
                <select value={filterBy} onChange={event => setFilterBy(event.target.value)} class="category">
                    <option value="name" >Name</option>
                    <option value="category" >Category </option>
                    <option value="provider">Provider</option>
                </select>
                <input type="text" className='search-for' placeholder="Search your product..."
                    aria-label="Search..."
                    value={searchValue}
                    onChange={e => setSearchValue(e.target.value)}></input>
                <img src={magnifier} alt="magnifier" type="submit" />
            </form>
        </>
    )
}