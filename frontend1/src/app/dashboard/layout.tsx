import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"
import SidebarForDashboard from "@/components/SidebarForDashboard"

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <SidebarProvider >
      <SidebarForDashboard />
      <SidebarInset>
      <main>
        <SidebarTrigger />
        {children}
      </main>
      </SidebarInset>
    </SidebarProvider>
  )
}