import HeaderForDashboard from "@/components/HeaderForDashboard"
import { columns, Payment } from "./columns"
import { DataTable } from "./data-table"

async function getData(): Promise<Payment[]> {
  // Fetch data from your API here.
  return [
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Diego's File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    {
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "aay 11, 2004",
    },{
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },{
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },{
      id: "728ed52f",
      name: "Followers File",
      size: "1 MB",
      created: "May 11, 2004",
    },
    
    // ...
  ]
}

export default async function DemoPage() {
  const data = await getData()

  return (
    <>
      <HeaderForDashboard title="File Manger" />
      <div className="container mx-auto py-10">
        <DataTable columns={columns} data={data} />
      </div>
    </>
  )
}