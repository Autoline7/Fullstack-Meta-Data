import { fetcher } from '@/lib/utils';
import { InstagramAccount } from '@/types/instagramAccount';
import useSWR from 'swr';

interface UseGetFollowersParams {
  searchQuery?: string;
  cursorId?: number | null;
  pageSize?: number;
  sortOrder?: 'ASC' | 'DESC';
}

const useGetFollowers = ({ searchQuery = '', cursorId = 0, pageSize = 10, sortOrder = 'DESC' }: UseGetFollowersParams) => {
  const params = new URLSearchParams();

  if (searchQuery) params.append('search', searchQuery);
  if (cursorId !== null) params.append('cursorId', cursorId.toString());
  params.append('pageSize', pageSize.toString());
  params.append('sortOrder', sortOrder);

  const { data, error, isLoading, mutate } = useSWR<InstagramAccount[]>(
    `/api/instagram/get-followers?${params.toString()}`,
    fetcher,
    {
      revalidateOnFocus: false,
      keepPreviousData: true,
      onError: (err) => {
        console.error('Error fetching followers:', err);
      },
    }
  );

  // Transform data to ensure id is a string
  const transformedData: InstagramAccount[] | undefined = data?.map((item) => ({
    ...item,
    id: String(item.id), // Convert id to string
  }));

  // Calculate next cursor ID based on the last item in the data
  const nextCursorId = transformedData && transformedData.length > 0 
    ? Number(transformedData[transformedData.length - 1].id) 
    : null;

  return {
    users: transformedData || [],
    nextCursorId,
    isLoading,
    isError: error,
    mutate,
  };
};

export default useGetFollowers;