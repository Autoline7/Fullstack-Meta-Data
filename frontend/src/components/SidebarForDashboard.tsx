import { Sidebar, SidebarContent, SidebarFooter, SidebarGroupLabel, SidebarHeader, SidebarMenu, SidebarMenuButton, SidebarMenuItem, SidebarSeparator } from '@/components/ui/sidebar';
import { Folder, Home, LayoutDashboard, LogOutIcon, Settings,} from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';

const items = [
  {
    title: "Home",
    url: "/",
    icon: Home,
  },
  {
    title: "Dashboard",
    url: "/dashboard",
    icon: LayoutDashboard,
  },
  {
    title: "Files",
    url: "/files",
    icon: Folder,
  },
  {
    title: "Settings",
    url: "/settings",
    icon: Settings,
  },
]

const SidebarForDashboard = () => {

    const location = useLocation();

  return (
        <Sidebar className='hover:shadow-lg hover:shadow-convrt-purple/20 active:scale-[0.98]' side='left' variant='sidebar' collapsible='icon'>
                <SidebarHeader>
                    <div className="flex items-center h-full mb-2">
                        <a href="/" className="flex items-center h-full rounded-sm">
                        <img 
                            src="/lovable-uploads/SandieTechWB.png" 
                            alt="Sandie Tech Logo" 
                            className="h-full object-contain"
                        />
                        </a>
                    </div>
                    
                </SidebarHeader>
            <SidebarSeparator />
            <SidebarContent>
                <SidebarGroupLabel>Navigation Menu</SidebarGroupLabel>
            <SidebarMenu >
                {items.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton isActive={location.pathname === item.url}  asChild>
                    <Link to={item.url}>
                      <item.icon />
                      <span>{item.title}</span>
                    </Link>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
            </SidebarContent>
            <SidebarFooter>
                <SidebarMenuButton tooltip="Logout">
                    <LogOutIcon />
                    <span>Logout</span>
                </SidebarMenuButton>
            </SidebarFooter>
        </Sidebar>
        
  )
}

export default SidebarForDashboard


