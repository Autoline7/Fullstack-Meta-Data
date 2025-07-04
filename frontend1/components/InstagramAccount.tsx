import React from 'react'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'

type InstagramAccountOptions = {
    userName: string,
    description: string,
    linkToProfile: string,
    timestamp: string,
    timestampDesc: string,
}

const InstagramAccount = ({userName, description, linkToProfile, timestampDesc, timestamp} : InstagramAccountOptions) => {
  return (
    <Card className='hover:shadow-lg hover:shadow-convrt-purple/20 hover:scale-[1.02] active:scale-[0.98]'>
        <CardHeader>
            <CardTitle className='text-center text-lg'>{userName}</CardTitle>
            <CardDescription className='text-center'>{description}</CardDescription>
        </CardHeader>
        <CardContent>
            <p className='text-center'><span className='text-sm font-bold'>{timestampDesc}:</span> <span className='text-xs'>{timestamp}</span></p>
        </CardContent>
        <CardFooter>
            <div className='w-full button-primary'>
                <a className='w-full flex justify-center' target="_blank" href={linkToProfile}>Link to Profile</a>
            </div>
            
        </CardFooter>
    </Card>
  )
}

export default InstagramAccount
