import { Clock, Heart, UserPlus, Users, UserX } from 'lucide-react'
import Link from 'next/link';

const iconMap = {
  users: Users,
  userPlus: UserPlus,
  heart: Heart,
  clock: Clock,
  userX: UserX,
};

type DashboardOptionProps = {
  iconType: keyof typeof iconMap;
  title: string;
  count: number;
  link: string;
  disabled?: boolean
};


const DashboardOption = ({iconType, title, count, link, disabled} : DashboardOptionProps) => {
    const IconComponent = iconMap[iconType];//determines what icon we use

  return (
    <Link href={disabled ? "" : link} className='border flex rounded-md border-black shadow-xl h-40 cursor-pointer'>
        <div className='flex items-center h-full ml-8'>
            <IconComponent className="w-10 h-10" />
        </div>
        <div className='flex flex-col gap-2 justify-center items-center h-full w-28 ml-3'>

            <div>
                <h1 className='text-xl text-center font-bold'>{title}</h1>
            </div>
            <div className='text-xl font-semibold text-red-700'>
                <h1 className='text-center'>{count}</h1>
            </div>
        </div>
    </Link>
  )
}

export default DashboardOption
