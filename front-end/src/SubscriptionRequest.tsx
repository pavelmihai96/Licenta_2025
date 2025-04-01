export default interface SubscriptionRequest {
    id: {
        user_id: number,
        provider_id: number
    },
    status?: "active" | "inactive",
    index_old?: number,
    index_read?: number
}