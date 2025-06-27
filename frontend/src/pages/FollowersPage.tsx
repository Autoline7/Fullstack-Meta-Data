import HeaderForDashboard from '@/components/HeaderForDashboard'
import InstagramAccounts from '@/components/InstagramAccounts'
import SidebarForDashboard from '@/components/SidebarForDashboard'
import { SidebarInset, SidebarProvider } from '@/components/ui/sidebar'
import React, { useState } from 'react'

const FollowersPage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  console.log(searchTerm);
  return (
    <SidebarProvider>
            <SidebarForDashboard />
             <SidebarInset>
            {/* Main content (pages, routes, text, etc) */}
            
            {/* Header */}
            <HeaderForDashboard setSearchTerm={setSearchTerm} placeholder="Search By Username" title="Followers Page"/>
            

            {/* Main */}
            <InstagramAccounts searchTerm={searchTerm}/>


        </SidebarInset>
        </SidebarProvider>
  )
}

export default FollowersPage
