import {RevolvingDot} from 'react-loader-spinner';

const Loading: React.FC = () => {
    return(
        <RevolvingDot 
            color='#FF00DD'
            height={100}
            width={100}
        />
    );
};

export default Loading;