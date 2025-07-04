import React, { useEffect, useState } from 'react';
import InstagramAccountComponent from '@/components/InstagramAccount';
import useGetFollowers from '@/hooks/useGetFollowers';
import { Pagination, PaginationContent, PaginationItem, PaginationNext, PaginationPrevious } from './ui/pagination';
import { InstagramAccount } from '@/types/instagramAccount';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from './ui/select';

type InstagramAccountsOptions = {
  searchTerm: string;
};

const InstagramAccounts = ({ searchTerm }: InstagramAccountsOptions) => {
  const [cursorId, setCursorId] = useState<number | null>(0);
  const [sortOrder, setSortOrder] = useState<'ASC' | 'DESC'>('DESC');
  const pageSize = 20;

  const { users, nextCursorId, isLoading, isError } = useGetFollowers({
    searchQuery: searchTerm,
    cursorId,
    pageSize,
    sortOrder,
  });

  // Reset cursor when search term or sort order changes
  useEffect(() => {
    setCursorId(0);
  }, [searchTerm, sortOrder]);

  if (isLoading) {
    return <div className="text-center p-8">Users are loading...</div>;
  }

  if (isError) {
    return <div className="text-center p-8">Error loading users...</div>;
  }

  if (!users || users.length === 0) {
    return (
      <div className="space-y-6">
        <div className="text-center p-8">
          <p>No accounts found matching "{searchTerm}"</p>
        </div>
      </div>
    );
  }

  return (
    <div >
      <div className="flex justify-end pt-4 pr-4">
        <Select
          value={sortOrder}
          onValueChange={(value: 'ASC' | 'DESC') => setSortOrder(value)}
        >
          <SelectTrigger className="w-[180px]">
            <SelectValue placeholder="Sort by" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="DESC">Newest to Oldest</SelectItem>
            <SelectItem value="ASC">Oldest to Newest</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-5 gap-4 p-8">
        {users.map((acc: InstagramAccount, index) => (
          <InstagramAccountComponent
            key={`${acc.username}-${acc.id}`}
            userName={acc.username}
            description="This person follows you"
            linkToProfile={acc.href}
            timestampDesc="Follower Since"
            timestamp={acc.timestamp ? new Date(acc.timestamp * 1000).toLocaleDateString() : 'Unknown'}
          />
        ))}
      </div>

      <Pagination>
        <PaginationContent className="justify-center">
          <PaginationItem>
            <PaginationPrevious
              href="#"
              onClick={(e) => {
                e.preventDefault();
                setCursorId(0); // Reset to first page
              }}
              className={cursorId === 0 ? 'pointer-events-none opacity-50' : ''}
            />
          </PaginationItem>

          <PaginationItem>
            <PaginationNext
              href="#"
              onClick={(e) => {
                e.preventDefault();
                if (nextCursorId !== null) {
                  setCursorId(nextCursorId);
                }
              }}
              className={nextCursorId === null ? 'pointer-events-none opacity-50' : ''}
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  );
};

export default InstagramAccounts;