import React from 'react'
import { Search } from 'lucide-react';

type HeaderForDashboardOptions = {
    title: string;
    setSearchTerm?: (value: string) => void;
    placeholder?: string;
}


const HeaderForDashboard = ({title, setSearchTerm, placeholder} : HeaderForDashboardOptions) => {
  return (
    <>
        <header className="flex items-center justify-between w-full border-b bg-background px-6 py-4">
            <h1 className="text-2xl font-semibold tracking-tight">{title}</h1>
            

            {/* Search Bar */}
            {placeholder && (
              <div className="relative w-full max-w-sm">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground h-5 w-5" />
              <input
                  type="text"
                  placeholder={placeholder}
                  onChange={(e) => setSearchTerm?.(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 border border-input rounded-md bg-background text-sm focus:outline-none focus:ring-2 focus:ring-ring"
              />
              </div>
            )}
            
            {/* You can add buttons, icons, avatar here */}
        </header>
    </>
    
  )
}

export default HeaderForDashboard
