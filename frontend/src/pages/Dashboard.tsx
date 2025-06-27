import { useEffect, useState } from 'react';
import axios from 'axios';
import SidebarForDashboard from '@/components/SidebarForDashboard';
import { SidebarInset, SidebarProvider } from '@/components/ui/sidebar';
import DashboardOption from '@/components/DashboardOption';
import HeaderForDashboard from '@/components/HeaderForDashboard';

interface Item {
  id: string;
  name: string;
  // Add other fields as per your backend response
}

export default function Dashboard() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/unfollowers');

        if (!response) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.data;
        console.log(data);
        setItems(data);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch data. Please check your backend configuration.');
        setLoading(false);
      }
    };

     fetchData();
  }, []);

  

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