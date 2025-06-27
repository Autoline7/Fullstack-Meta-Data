import React from 'react'
import InstagramAccount from './InstagramAccount'

const accounts = [
  { username:'diego.sanchez___', name: "Diego Sanchez", description:'This person follows you', linkToProfile:'https://www.instagram.com/diego.sanchez___/', timestampDesc:'Follower Since', timestamp:'May 11, 2004'},
  { username: "johndoe", name: "John Doe", description:"This person follows you", linkToProfile:"https://www.instagram.com/diego.sanchez___/", timestampDesc:'Follower Since', timestamp:'May 11, 2004'},
  { username: "janedoe", name: "Jane Doe", description:"This person follows you", linkToProfile:"https://www.instagram.com/diego.sanchez___/", timestampDesc:'Follower Since', timestamp:'May 11, 2004'},
  { username: "foodiegram", name: "Food Blog", description:"This person follows you", linkToProfile:"https://www.instagram.com/diego.sanchez___/", timestampDesc:'Follower Since', timestamp:'May 11, 2004'},
]

type InstagramAccountsOptions = {
  searchTerm: string;
}

const InstagramAccounts = ({searchTerm} : InstagramAccountsOptions) => {
  const filteredAccounts = accounts.filter((acc) => 
    acc.username.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-5 gap-4 p-8'>
                {filteredAccounts.map((acc, _index) => (
                  <InstagramAccount key={_index} userName={acc.username} description={acc.description} linkToProfile={acc.linkToProfile} timestampDesc={acc.timestampDesc} timestamp={acc.timestamp}/>
                ))}
        </div>
  )
}

export default InstagramAccounts
