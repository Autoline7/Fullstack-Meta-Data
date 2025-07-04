"use client";
import HeaderForDashboard from '@/components/HeaderForDashboard'
import InstagramAccounts from '@/components/InstagramAccounts'
import React, { useState } from 'react'

const FollowersPage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  return (
    <>      
            {/* Header */}
            <HeaderForDashboard setSearchTerm={setSearchTerm} placeholder="Search By Username" title="Followers Page"/>
            {/* Main */}
            <InstagramAccounts searchTerm={searchTerm}/>
            </>
  )
}

export default FollowersPage
