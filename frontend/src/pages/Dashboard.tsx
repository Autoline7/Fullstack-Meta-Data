import SidebarForDashboard from '@/components/SidebarForDashboard';
import { SidebarInset, SidebarProvider } from '@/components/ui/sidebar';
import DashboardOption from '@/components/DashboardOption';
import HeaderForDashboard from '@/components/HeaderForDashboard';


export default function Dashboard() {
  return (
    <div className="min-h-screen ">
        
        <SidebarProvider>
            <SidebarForDashboard />
             <SidebarInset>
            {/* Main content (pages, routes, text, etc) */}
            
            {/* Header */}
           <HeaderForDashboard title="Dashboard" setSearchTerm={null} placeholder={null}/>

           {/* Upload File */}
           

            {/* Main */}
            <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 p-8'>
                <DashboardOption link='/followers' iconType='users' title='Followers' count={411}/>
                <DashboardOption disabled={true} link='/following' iconType='userPlus' title='Following' count={287}/>
                <DashboardOption disabled={true} link='/close-friends' iconType='heart' title='Close Friends' count={6}/>
                <DashboardOption disabled={true} link='/pending-requests' iconType='clock' title='Pending Follow Requests' count={33}/>
                <DashboardOption disabled={true} link='/hide-story'iconType='userX' title='Hide Story From' count={2}/>
                <DashboardOption link='/no-follow-back'iconType='userX' title='Dont Follow You back' count={12}/>
            </div>


        </SidebarInset>
        </SidebarProvider>
    </div>
  );
}