


import './userInfoPage.scss';

const userInfoPage = () => {
  const userData = {
    fullName: localStorage.getItem('displayName'),
    username: localStorage.getItem('username'),
    role: localStorage.getItem('role'),
    id: localStorage.getItem('id'),
  };

  return (
      <div className="user-info-page">
        <div className="admin-container">
          <p className='main-word'>{userData.username}</p>
          <div className="card-info">
            <h3 className='card-detail'>Full Name</h3>
            <div className='detail-info'>{userData.fullName}</div>
          </div>
          <div className="card-info">
            <h3 className='card-detail'>Role</h3>
            <div className='detail-info'>{userData.role}</div>
          </div>
          <div className="card-info">
            <h3 className='card-detail'>ID</h3>
            <div className='detail-info'>{userData.id}</div>
          </div>
        </div>
      </div>
  );
}

export default userInfoPage;
