import { Navigate, Outlet } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const PrivateRoute = () => {
    const { user, loading } = useAuth();

    if (loading) {
        // Optionally, you can return a loading spinner or some placeholder here
        return <div>Loading...</div>;
    }

    return user ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;